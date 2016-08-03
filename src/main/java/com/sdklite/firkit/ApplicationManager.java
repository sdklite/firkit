package com.sdklite.firkit;

import java.io.Serializable;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public interface ApplicationManager {

    public class Application implements Serializable {

        private static final long serialVersionUID = 8151091556969643005L;

        @SerializedName("id")
        private String id;

        @SerializedName("type")
        private String type;

        @SerializedName("name")
        private String name;

        @SerializedName("desc")
        private String desc;

        @SerializedName("bundle_id")
        private String bundleId;

        @SerializedName("is_opened")
        private boolean opened;

        @SerializedName("has_combo")
        private boolean combo;

        @SerializedName("created_at")
        private long createdAt;

        @SerializedName("icon_url")
        private String iconUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return desc;
        }

        public void setDescription(String desc) {
            this.desc = desc;
        }

        public String getBundleId() {
            return bundleId;
        }

        public void setBundleId(String bundleId) {
            this.bundleId = bundleId;
        }

        public boolean isOpened() {
            return opened;
        }

        public void setOpened(boolean opened) {
            this.opened = opened;
        }

        public boolean hasCombo() {
            return combo;
        }

        public void setCombo(boolean combo) {
            this.combo = combo;
        }

        public long getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(long createdAt) {
            this.createdAt = createdAt;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }

    public class Version implements Serializable {

        private static final long serialVersionUID = 1888971291781056841L;

        @SerializedName("name")
        private String name;

        @SerializedName("version")
        private int versionCode;

        @SerializedName("versionShort")
        private String versionName;

        @SerializedName("changelog")
        private String changelog;

        @SerializedName("build")
        private int buildNumber;

        @SerializedName("install_url")
        private String installUrl;

        @SerializedName("update_url")
        private String updateUrl;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getVersionCode() {
            return this.versionCode;
        }

        public void setVersionCode(int version) {
            this.versionCode = version;
        }

        public String getVersionName() {
            return this.versionName;
        }

        public void setVersionName(String versionShort) {
            this.versionName = versionShort;
        }

        public String getChangeLog() {
            return this.changelog;
        }

        public void setChangeLog(String changelog) {
            this.changelog = changelog;
        }

        public int getBuildNumber() {
            return this.buildNumber;
        }

        public void setBuildNumber(int build) {
            this.buildNumber = build;
        }

        public String getInstallUrl() {
            return this.installUrl;
        }

        public void setInstallUrl(String installUrl) {
            this.installUrl = installUrl;
        }

        public String getUpdateUrl() {
            return this.updateUrl;
        }

        public void setUpdateUrl(String updateUrl) {
            this.updateUrl = updateUrl;
        }

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }

    public int getLocalVersionCode();

    public void latest(final Callback<Version> callback);

    public void list(final Callback<List<Application>> callback);

    public void get(final String id, final Callback<Application> callback);

    public void upgrade(final Version version, final Callback<?> callback);

    public void install(final String url, final Callback<?> callback);
}
