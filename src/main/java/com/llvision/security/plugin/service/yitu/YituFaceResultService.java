package com.llvision.security.plugin.service.yitu;

import com.google.common.base.Strings;
import com.llvision.security.plugin.model.FaceResult;
import com.llvision.security.plugin.model.Person;
import com.llvision.security.plugin.model.PersonImage;
import com.llvision.security.plugin.service.*;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Created by llvision on 17/5/20.
 */
@Service
public class YituFaceResultService implements FaceResultService, PersonService, SystemConfigService {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final String CONFIG_KEY_BASE_URL = "yitu.baseUrl";
    private static final String CONFIG_KEY_USERNAME = "yitu.username";
    private static final String CONFIG_KEY_PASSWORD = "yitu.password";
    private static final String CONFIG_KEY_AVAILABLE_REPO_IDS = "yitu.availableRepoIds";
    private static final String CONFIG_KEY_BLACK_LIST_REPO_IDS = "yitu.blackListRepoIds";

    private final YituProperties yituProperties;
    private final OkHttpClient httpClient;
    private String sessionId;
    private long lastRequestTimeMillis = 0;

    @Autowired
    private SystemConfigUtil systemConfigUtil;

    public YituFaceResultService(YituProperties yituProperties) {
        this.yituProperties = yituProperties;
        this.httpClient = new OkHttpClient();
    }

    public boolean isLoggedIn() {
        return !Strings.isNullOrEmpty(sessionId)
            && System.currentTimeMillis() - lastRequestTimeMillis < 5 * 60 * 1000;
    }

    private String md5(String in) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(in.getBytes());
            byte[] a = digest.digest();
            int len = a.length;
            StringBuilder sb = new StringBuilder(len << 1);
            for (int i = 0; i < len; i++) {
                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
                sb.append(Character.forDigit(a[i] & 0x0f, 16));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
        return null;
    }

    private OkHttpClient getHttpClient(boolean withoutLogin) throws YituLoginException {
        if (!withoutLogin && !isLoggedIn()) {
            if (!login()) {
                throw new YituLoginException();
            }
        }
        return httpClient;
    }

    public boolean login() throws YituLoginException {
        sessionId = null;
        try {
            JSONObject params = new JSONObject();
            params.put("name", systemConfigUtil.getValue(CONFIG_KEY_USERNAME).orElse(yituProperties.getUsername()));
            params.put("password", md5(systemConfigUtil.getValue(CONFIG_KEY_PASSWORD).orElse(yituProperties.getPassword())));
            JSONObject response = post( yituProperties.getResourceManagerPath() + "/user/login", params, true);
            if (isResponseOK(response)) {
                sessionId = String.valueOf(response.optLong("session_id"));
                lastRequestTimeMillis = System.currentTimeMillis();
                return true;
            }
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            throw new YituLoginException();
        } catch (IOException e) {
            e.printStackTrace();
            throw new YituLoginException();
        }
    }

    public JSONObject listRepositories() throws YituLoginException {
        try {
            JSONObject result = get(yituProperties.getFacePath() + "/v1/framework/face_image/repository");
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<FaceResult> compareFace(String imageBase64, int numResults) throws NoTargetException {
        try {
            // Upload face
            JSONObject options = new JSONObject();
            options.put("max_faces_allowed", 1);
            JSONObject paramUpload = new JSONObject();
            paramUpload.put("options", options);
            paramUpload.put("picture_image_content_base64", imageBase64);
            JSONObject responseUpload = post(yituProperties.getFacePath() + "/v1/framework/face_image/repository/picture/synchronized", paramUpload);

            if (!isResponseOK(responseUpload)) {
                if (responseUpload.optInt("rtn", 0) == -6020) {
                    throw new NoTargetException("请求错误");
                } else {
                    throw new NoTargetException("服务器没有检测到人脸");
                }
            }
            JSONArray faces = responseUpload.getJSONArray("results");
            if (faces == null || faces.length() <= 0) {
                throw new NoTargetException("服务器没有检测到人脸");
            }
            String faceImageId = faces.getJSONObject(0).optString("face_image_id_str", "");
            if (Strings.isNullOrEmpty(faceImageId)) {
                throw new NoTargetException("服务器没有检测到人脸");
            }

            JSONArray repositoryIds = new JSONArray();
            for (Integer id : systemConfigUtil.getIntArrayValue(CONFIG_KEY_AVAILABLE_REPO_IDS).orElse(yituProperties.getAvailableRepoIds())) {
                repositoryIds.put(id);
            }
            // Retrieval
            JSONObject paramRetrieval = new JSONObject(
                "{" +
                    "\"fields\": [\n" +
                    "\"face_image_id\",\n" +
                    "\"repository_id\",\n" +
                    "\"timestamp\",\n" +
                    "\"person_id\",\n" +
                    "\"name\",\n" +
                    "\"gender\",\n" +
                    "\"nation\",\n" +
                    "\"camera_id\",\n" +
                    "\"is_hit\",\n" +
                    "\"born_year\",\n" +
                    "\"similarity\",\n" +
                    "\"annotation\",\n" +
                    "\"picture_uri\",\n" +
                    "\"face_image_uri\"\n" +
                    "],\n" +
                    "\"condition\": {},\n" +
                    "\"retrieval\": {\n" +
                    "\"threshold\": 0,\n" +
                    "\"face_image_id_str\": \"" + faceImageId + "\"" +
                    ",\n" +
                    "\"repository_ids\": " + repositoryIds.toString() + "\n" +
                    "},\n" +
                    "\"order\": {\n" +
                    "\"similarity\": -1\n" +
                    "},\n" +
                    "\"start\": 0,\n" +
                    "\"limit\": " + numResults +
                    "}"
            );

            JSONObject responseRetrieval = post(yituProperties.getFacePath() + "/v1/framework/face/retrieval", paramRetrieval);

            if (!isResponseOK(responseRetrieval)) {
                throw new NoTargetException("识别失败");
            }

            JSONArray resultFaces = responseRetrieval.getJSONArray("results");
            List<FaceResult> faceList = new ArrayList<>();
            for (int i = 0; i < resultFaces.length(); i++) {
                JSONObject item = resultFaces.getJSONObject(i);
                faceList.add(parseFaceResult(item));
            }
            return faceList;
        } catch (JSONException e) {
            throw new NoTargetException("数据格式错误", e);
        } catch (IOException e) {
            throw new NoTargetException("网络错误", e);
        } catch (YituLoginException e) {
            throw new NoTargetException("登录失败", e);
        }
    }

    private FaceResult parseFaceResult(JSONObject item) {
        FaceResult f = new FaceResult();
        f.setId(item.optString("face_image_id_str", ""));
        f.setPersonInfoId(item.optString("face_image_id_str", ""));
        f.setImageId(item.optString("face_image_uri", ""));
        f.setSimilarity(item.optDouble("similarity", 0.0) / 100.0);
        f.setPerson(parsePerson(item));
        return f;
    }

    private Person parsePerson(JSONObject item) {
        Person p = new Person();
        String repoId = item.optString("repository_id", "-1");
        if (repoId.endsWith("@DEFAULT")) {
            repoId = repoId.substring(0, repoId.indexOf("@DEFAULT"));
        }
        // 如果返回人像库ID是黑名单库的ID，设置为警示
        p.setAttention(
            systemConfigUtil.getIntArrayValue(CONFIG_KEY_BLACK_LIST_REPO_IDS).orElse(yituProperties.getBlackListRepoIds())
                .contains(Integer.parseInt(repoId)));
        p.setName(item.optString("name", ""));
        p.setImageId(item.optString("face_image_uri", ""));
        p.setId(item.optString("face_image_id_str", ""));
        p.setGender(item.optInt("gender", 0));
        p.setPersonId(item.optString("person_id"));
        if (item.has("born_year")) {
            p.setBirthday(ZonedDateTime.of(
                item.optInt("born_year"),
                1,
                1,
                0,
                0,
                0,
                0,
                ZoneId.systemDefault()));
        }
        return p;
    }

    public boolean isResponseOK(JSONObject response) {
        return response.optString("message", "").equals("OK");
    }

    private JSONObject get(String url) throws IOException, JSONException, YituLoginException {
        Response response = getRaw(url);
        if (response.isSuccessful()) {
            lastRequestTimeMillis = System.currentTimeMillis();
            return new JSONObject(response.body().string());
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    private Response getRaw(String url) throws YituLoginException, IOException {
        OkHttpClient client = getHttpClient(false);  // In orrder to fetch sessionId, do this first
        Request.Builder builder = new Request.Builder()
            .url(systemConfigUtil.getValue(CONFIG_KEY_BASE_URL).orElse(yituProperties.getBaseUrl()) + url);
        if (sessionId != null) {
            builder.addHeader("Cookie", "session_id=" + sessionId);
        }
        return client.newCall(builder.build()).execute();
    }

    private JSONObject post(String url, JSONObject json) throws IOException, JSONException, YituLoginException {
        return post(url, json, false);
    }

    private JSONObject post(String url, JSONObject json, boolean withoutLogin)
        throws IOException, JSONException, YituLoginException {
        OkHttpClient client = getHttpClient(withoutLogin);  // In orrder to fetch sessionId, do this first
        RequestBody body = RequestBody.create(JSON, json.toString());
        Request.Builder builder = new Request.Builder()
            .url(systemConfigUtil.getValue(CONFIG_KEY_BASE_URL).orElse(yituProperties.getBaseUrl()) + url)
            .post(body);
        if (sessionId != null) {
            builder.addHeader("Cookie", "session_id=" + sessionId);
        }
        Response response = client.newCall(builder.build()).execute();
        if (response.isSuccessful()) {
            lastRequestTimeMillis = System.currentTimeMillis();
            return new JSONObject(response.body().string());
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    @Override
    public List<FaceResult> recognizeFaces(byte[] sourcePic, int limit) throws NoTargetException {
        return compareFace(Base64.getEncoder().encodeToString(sourcePic), limit);
    }

    private JSONObject queryFace(String id) throws JSONException, IOException, YituLoginException, NoTargetException {
        JSONObject param = new JSONObject(
            "{\"fields\":[" +
                    "\"face_image_id\"," +
                    "\"repository_id\"," +
                    "\"timestamp\"," +
                    "\"person_id\"," +
                    "\"name\"," +
                    "\"gender\"," +
                    "\"nation\"," +
                    "\"born_year\"," +
                    "\"picture_uri\"," +
                    "\"face_image_uri\"]," +
                "\"condition\":{" +
                    "\"face_image_id\": " + id + "}," +
                "\"query_meta\":{}," +
                "\"order\":{\"timestamp\":-1}," +
                "\"start\":0," +
                "\"limit\":1}");

        JSONObject response =
            post(yituProperties.getFacePath() + "/v1/framework/face/query/global", param);

        if (!isResponseOK(response)) {
            throw new NoTargetException("获取人脸失败");
        }

        JSONArray resultFaces = response.getJSONArray("results");
        return resultFaces.getJSONObject(0);
    }

    @Override
    public FaceResult getFaceResult(String faceResultId) {
        try {
            return parseFaceResult(queryFace(faceResultId));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (YituLoginException e) {
            e.printStackTrace();
        } catch (NoTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Person getPerson(String id) {
        try {
            return parsePerson(queryFace(id));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (YituLoginException e) {
            e.printStackTrace();
        } catch (NoTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PersonImage getImage(String imageId) throws IOException {
        try {
            if (imageId.endsWith("@DEFAULT")) {
                imageId = imageId.substring(0, imageId.indexOf("@DEFAULT"));
            }
            ResponseBody body = getRaw( yituProperties.getStoragePath() +
                "/v1/image?uri_base64=" + Base64.getEncoder().encodeToString(imageId.getBytes())).body();

            PersonImage image = new PersonImage();
            image.setId(imageId);
            image.setContentType(body.contentType().toString());
            image.setData(body.bytes());
            return image;
        } catch (YituLoginException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void initConfig() {
        // TODO(wangag): 初始化依图相关系统配置
    }

    @Override
    public void onValueChanged(String key) {
        // 如果地址，用户名，密码发生变更，重新登录
        if (CONFIG_KEY_BASE_URL.equals(key) ||
            CONFIG_KEY_USERNAME.equals(key) ||
            CONFIG_KEY_PASSWORD.equals(key)) {
            sessionId = null;
        }
    }
}
