package com.example.ng_tiofack.mynews.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MostPopular {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("num_results")
    @Expose
    private Integer numResults;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Integer getNumResults() {
        return numResults;
    }

    public void setNumResults(Integer numResults) {
        this.numResults = numResults;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public static class Result {

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("adx_keywords")
        @Expose
        private String adxKeywords;
        @SerializedName("column")
        @Expose
        private Object column;
        @SerializedName("section")
        @Expose
        private String section;
        @SerializedName("byline")
        @Expose
        private String byline;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("abstract")
        @Expose
        private String _abstract;
        @SerializedName("published_date")
        @Expose
        private String publishedDate;
        @SerializedName("source")
        @Expose
        private String source;
        @SerializedName("id")
        @Expose
        private Float id;
        @SerializedName("asset_id")
        @Expose
        private Float assetId;
        @SerializedName("views")
        @Expose
        private Integer views;
        //@SerializedName("des_facet")
        @Expose
        private List<String> mdesFacet = null;
        // @SerializedName("org_facet")
        @Expose
        private List<String> morgFacet = null;
        //@SerializedName("per_facet")
        @Expose
        private List<String> mperFacet = null;
        //@SerializedName("geo_facet")
        @Expose
        private List<String> mgeoFacet;
        @SerializedName("media")
        @Expose
        private List<Medium> media = null;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAdxKeywords() {
            return adxKeywords;
        }

        public void setAdxKeywords(String adxKeywords) {
            this.adxKeywords = adxKeywords;
        }

        public Object getColumn() {
            return column;
        }

        public void setColumn(Object column) {
            this.column = column;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public String getByline() {
            return byline;
        }

        public void setByline(String byline) {
            this.byline = byline;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAbstract() {
            return _abstract;
        }

        public void setAbstract(String _abstract) {
            this._abstract = _abstract;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public void setPublishedDate(String publishedDate) {
            this.publishedDate = publishedDate;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public Float getId() {
            return id;
        }

        public void setId(Float id) {
            this.id = id;
        }

        public Float getAssetId() {
            return assetId;
        }

        public void setAssetId(Float assetId) {
            this.assetId = assetId;
        }

        public Integer getViews() {
            return views;
        }

        public void setViews(Integer views) {
            this.views = views;
        }

        public List<String> getDesFacet() {
            return mdesFacet;
        }

        public void setDesFacet(List<String> desFacet) {
            this.mdesFacet = desFacet;
        }

        public List<String> getOrgFacet() {

            // StringConverter myStringConverter = new StringConverter();

            return (morgFacet);

        }

        public void setOrgFacet(List<String> orgFacet) {
            this.morgFacet = orgFacet;
        }

        public List<String> getPerFacet() {
            return mperFacet;
        }

        public void setPerFacet(List<String> perFacet) {
            this.mperFacet = perFacet;
        }

        public List<String> getGeoFacet() {
            return mgeoFacet;
        }

        public void setGeoFacet(List<String> geoFacet) {
            this.mgeoFacet = geoFacet;
        }

        public List<Medium> getMedia() {
            return media;
        }

        public void setMedia(List<Medium> media) {
            this.media = media;
        }

        public static class OptionsDeserilizer implements JsonDeserializer<Result> {

            @Override
            public Result deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

                Result result = new Gson().fromJson(json, Result.class);
                JsonObject jsonObject = json.getAsJsonObject();

                if (jsonObject.has("org_facet")) {
                    JsonElement elem = jsonObject.get("org_facet");

                    if (elem != null && !elem.isJsonNull()) {

                        if (elem.isJsonPrimitive()) {
                            result.setOrgFacet(null);
                        } else {
                            JsonArray array = jsonObject.getAsJsonArray("org_facet");
                            List<String> data = new Gson().fromJson(array, new TypeToken<ArrayList<String>>() {
                            }.getType());
                            result.setOrgFacet(data);
                        }
                    }
                }

                if (jsonObject.has("des_facet")) {
                    JsonElement elem1 = jsonObject.get("des_facet");

                    if (elem1 != null && !elem1.isJsonNull()) {

                        if (elem1.isJsonPrimitive()) {
                            result.setDesFacet(null);
                        } else {
                            JsonArray array = jsonObject.getAsJsonArray("des_facet");
                            List<String> data = new Gson().fromJson(array, new TypeToken<ArrayList<String>>() {
                            }.getType());
                            result.setDesFacet(data);
                        }
                    }
                }
                if (jsonObject.has("per_facet")) {
                    JsonElement elem2 = jsonObject.get("per_facet");

                    if (elem2 != null && !elem2.isJsonNull()) {

                        if (elem2.isJsonPrimitive()) {
                            result.setPerFacet(null);
                        } else {
                            JsonArray array = jsonObject.getAsJsonArray("per_facet");
                            List<String> data = new Gson().fromJson(array, new TypeToken<ArrayList<String>>() {
                            }.getType());
                            result.setPerFacet(data);
                        }
                    }
                }

                if (jsonObject.has("geo_facet")) {
                    JsonElement elem3 = jsonObject.get("geo_facet");

                    if (elem3 != null && !elem3.isJsonNull()) {

                        if (elem3.isJsonPrimitive()) {
                            result.setGeoFacet(null);
                        } else {
                            JsonArray array = jsonObject.getAsJsonArray("geo_facet");
                            List<String> data = new Gson().fromJson(array, new TypeToken<ArrayList<String>>() {
                            }.getType());
                            result.setGeoFacet(data);
                        }
                    }
                }
                return result;
            }
        }

        public class Medium {

            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("subtype")
            @Expose
            private String subtype;
            @SerializedName("caption")
            @Expose
            private String caption;
            @SerializedName("copyright")
            @Expose
            private String copyright;
            @SerializedName("approved_for_syndication")
            @Expose
            private Integer approvedForSyndication;
            @SerializedName("media-metadata")
            @Expose
            private List<MediaMetadatum> mediaMetadata = null;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getSubtype() {
                return subtype;
            }

            public void setSubtype(String subtype) {
                this.subtype = subtype;
            }

            public String getCaption() {
                return caption;
            }

            public void setCaption(String caption) {
                this.caption = caption;
            }

            public String getCopyright() {
                return copyright;
            }

            public void setCopyright(String copyright) {
                this.copyright = copyright;
            }

            public Integer getApprovedForSyndication() {
                return approvedForSyndication;
            }

            public void setApprovedForSyndication(Integer approvedForSyndication) {
                this.approvedForSyndication = approvedForSyndication;
            }

            public List<MediaMetadatum> getMediaMetadata() {
                return mediaMetadata;
            }

            public void setMediaMetadata(List<MediaMetadatum> mediaMetadata) {
                this.mediaMetadata = mediaMetadata;
            }

            public class MediaMetadatum {

                @SerializedName("url")
                @Expose
                private String url;
                @SerializedName("format")
                @Expose
                private String format;
                @SerializedName("height")
                @Expose
                private Integer height;
                @SerializedName("width")
                @Expose
                private Integer width;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getFormat() {
                    return format;
                }

                public void setFormat(String format) {
                    this.format = format;
                }

                public Integer getHeight() {
                    return height;
                }

                public void setHeight(Integer height) {
                    this.height = height;
                }

                public Integer getWidth() {
                    return width;
                }

                public void setWidth(Integer width) {
                    this.width = width;
                }

            }

        }
    }
}


