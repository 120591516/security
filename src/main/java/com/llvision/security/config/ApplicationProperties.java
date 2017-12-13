package com.llvision.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Properties specific to JHipster.
 *
 * <p>
 *     Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Storage storage = new Storage();

    private final Recognition recognition = new Recognition();

    private final Plugin plugin = new Plugin();

    private final UserManage userManage=new UserManage();

    private final LocalFace localFace = new LocalFace();

    /**
     * 系统配置插件列表
     */
    private List<String> systemConfig = new ArrayList<>();

    public Storage getStorage() {
        return storage;
    }

    public Recognition getRecognition() {
        return recognition;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public UserManage getUserManage() {
        return userManage;
    }

    public LocalFace getLocalFace() {
        return localFace;
    }

    public List<String> getSystemConfig() {
        return systemConfig;
    }

    public void setSystemConfig(List<String> systemConfig) {
        this.systemConfig = systemConfig;
    }

    public static class Storage {
        /**
         * Folder location for storing files
         */
        private String location = "upload";

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }

    public static class Recognition {

        private final Face face = new Face();

        public Face getFace() {
            return face;
        }

        public static class Face {
            private int limit = 100;

            private int faces = 3;

            private double threshold = 0.1;

            public int getFaces() {
                return faces;
            }

            public void setFaces(int faces) {
                this.faces = faces;
            }

            public double getThreshold() {
                return threshold;
            }

            public void setThreshold(double threshold) {
                this.threshold = threshold;
            }

            public int getLimit() {
                return limit;
            }

            public void setLimit(int limit) {
                this.limit = limit;
            }
        }
    }

    public static class Plugin {

        private String carPlate = "random";
        private String face = "random";
        private String person = "single";

        public String getCarPlate() {
            return carPlate;
        }

        public void setCarPlate(String carPlate) {
            this.carPlate = carPlate;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getPerson() {
            return person;
        }

        public void setPerson(String person) {
            this.person = person;
        }
    }

    public static class UserManage {

        private Boolean selfService = false;

        public Boolean getSelfService() {
            return selfService;
        }

        public void setSelfService(Boolean selfService) {
            this.selfService = selfService;
        }
    }

    public static class LocalFace {

        private UpstreamPlugin upstreamPlugin = new UpstreamPlugin();

        public UpstreamPlugin getUpstreamPlugin() {
            return upstreamPlugin;
        }

        public static class UpstreamPlugin {
            private String face = "empty";
            private String person = "empty";

            public String getFace() {
                return face;
            }

            public void setFace(String face) {
                this.face = face;
            }

            public String getPerson() {
                return person;
            }

            public void setPerson(String person) {
                this.person = person;
            }
        }
    }
}
