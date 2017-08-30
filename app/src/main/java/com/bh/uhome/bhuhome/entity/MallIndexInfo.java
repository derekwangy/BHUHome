package com.bh.uhome.bhuhome.entity;

import java.util.List;

/**
 * 商城首页bean
 * Created by derek on 16/5/17.
 */
public class MallIndexInfo {

    /**
     * msg : 请求成功
     * code : 0
     * data : {"banner":{"bannerList":[{"link":"houpixapp://goods/930","imgId":"195962"},{"link":"houpixapp://goods/926","imgId":"195963"}],"bannerInfoHeight":"550","bannerInfoWidth":"750"},"board":{"boardList":[{"imgId":"197532","link":"houpixapp://topic/1"},{"imgId":"193229","link":""}],"boardInfoHeight":"1134","boardInfoWidth":"794"},"subjectList":[{"tag":{"name":"亿万少女的梦","word":"更多","link":"houpixapp://goods/12"},"goodsInfo":[{"id":"937","mainPic":"197659","price":"111","realPrice":"11100","unit":"元","name":"专用测试1","link":"houpixapp://goods/937"},{"id":"936","mainPic":"197068","price":"100","realPrice":"111100","unit":"元","name":"12","link":"houpixapp://goods/936"}],"bannerInfo":[{"id":"11","imgId":"193346","link":"houpixapp://webview/http%3A%2F%2Fwww.baidu.com"},{"id":"11","imgId":"193346","link":"houpixapp://webview/http%3A%2F%2Fwww.baidu.com"}],"brandInfo":[{"title":"我的","id":"2","imgId":"196986","link":"houpixapp://goods/23"},{"title":"我的","id":"2","imgId":"196986","link":"houpixapp://goods/23"}],"bannerInfoHeight":"180","bannerInfoWidth":"279"}],"recommendTags":[{"name":"今日上新","link":"mall/index/recommendlist?recommendId=0"},{"name":"亿万少女的噩梦","link":"mall/index/recommendlist?recommendId=1"}],"goodsList":[{"name":"H5购买0.01","mainPic":"195887","price":"1","realPrice":"200000","unit":"元","source":"澳大利亚","sales3day":"三天成交15笔","shippingInfo":"买家承担","link":"houpixapp://goods/930","label":"导购"},{"name":"专用测试1","mainPic":"197659","price":"111","realPrice":"11100","unit":"元","source":"澳大利亚","sales3day":"三天成交25笔","shippingInfo":"买家承担","link":"houpixapp://goods/937","label":"导购"}],"cartNum":"0"}
     */

    private String msg;
    private int code;
    /**
     * banner : {"bannerList":[{"link":"houpixapp://goods/930","imgId":"195962"},{"link":"houpixapp://goods/926","imgId":"195963"}],"bannerInfoHeight":"550","bannerInfoWidth":"750"}
     * board : {"boardList":[{"imgId":"197532","link":"houpixapp://topic/1"},{"imgId":"193229","link":""}],"boardInfoHeight":"1134","boardInfoWidth":"794"}
     * subjectList : [{"tag":{"name":"亿万少女的梦","word":"更多","link":"houpixapp://goods/12"},"goodsInfo":[{"id":"937","mainPic":"197659","price":"111","realPrice":"11100","unit":"元","name":"专用测试1","link":"houpixapp://goods/937"},{"id":"936","mainPic":"197068","price":"100","realPrice":"111100","unit":"元","name":"12","link":"houpixapp://goods/936"}],"bannerInfo":[{"id":"11","imgId":"193346","link":"houpixapp://webview/http%3A%2F%2Fwww.baidu.com"},{"id":"11","imgId":"193346","link":"houpixapp://webview/http%3A%2F%2Fwww.baidu.com"}],"brandInfo":[{"title":"我的","id":"2","imgId":"196986","link":"houpixapp://goods/23"},{"title":"我的","id":"2","imgId":"196986","link":"houpixapp://goods/23"}],"bannerInfoHeight":"180","bannerInfoWidth":"279"}]
     * recommendTags : [{"name":"今日上新","link":"mall/index/recommendlist?recommendId=0"},{"name":"亿万少女的噩梦","link":"mall/index/recommendlist?recommendId=1"}]
     * goodsList : [{"name":"H5购买0.01","mainPic":"195887","price":"1","realPrice":"200000","unit":"元","source":"澳大利亚","sales3day":"三天成交15笔","shippingInfo":"买家承担","link":"houpixapp://goods/930","label":"导购"},{"name":"专用测试1","mainPic":"197659","price":"111","realPrice":"11100","unit":"元","source":"澳大利亚","sales3day":"三天成交25笔","shippingInfo":"买家承担","link":"houpixapp://goods/937","label":"导购"}]
     * cartNum : 0
     */

    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * bannerList : [{"link":"houpixapp://goods/930","imgId":"195962"},{"link":"houpixapp://goods/926","imgId":"195963"}]
         * bannerInfoHeight : 550
         * bannerInfoWidth : 750
         */

        private BannerBean banner;
        /**
         * boardList : [{"imgId":"197532","link":"houpixapp://topic/1"},{"imgId":"193229","link":""}]
         * boardInfoHeight : 1134
         * boardInfoWidth : 794
         */

        private BoardBean board;
        private String cartNum;
        /**
         * tag : {"name":"亿万少女的梦","word":"更多","link":"houpixapp://goods/12"}
         * goodsInfo : [{"id":"937","mainPic":"197659","price":"111","realPrice":"11100","unit":"元","name":"专用测试1","link":"houpixapp://goods/937"},{"id":"936","mainPic":"197068","price":"100","realPrice":"111100","unit":"元","name":"12","link":"houpixapp://goods/936"}]
         * bannerInfo : [{"id":"11","imgId":"193346","link":"houpixapp://webview/http%3A%2F%2Fwww.baidu.com"},{"id":"11","imgId":"193346","link":"houpixapp://webview/http%3A%2F%2Fwww.baidu.com"}]
         * brandInfo : [{"title":"我的","id":"2","imgId":"196986","link":"houpixapp://goods/23"},{"title":"我的","id":"2","imgId":"196986","link":"houpixapp://goods/23"}]
         * bannerInfoHeight : 180
         * bannerInfoWidth : 279
         */

        private List<SubjectListBean> subjectList;
        /**
         * name : 今日上新
         * link : mall/index/recommendlist?recommendId=0
         */

        private List<RecommendTagsBean> recommendTags;
        /**
         * name : H5购买0.01
         * mainPic : 195887
         * price : 1
         * realPrice : 200000
         * unit : 元
         * source : 澳大利亚
         * sales3day : 三天成交15笔
         * shippingInfo : 买家承担
         * link : houpixapp://goods/930
         * label : 导购
         */

        private List<GoodsListBean> goodsList;

        public BannerBean getBanner() {
            return banner;
        }

        public void setBanner(BannerBean banner) {
            this.banner = banner;
        }

        public BoardBean getBoard() {
            return board;
        }

        public void setBoard(BoardBean board) {
            this.board = board;
        }

        public String getCartNum() {
            return cartNum;
        }

        public void setCartNum(String cartNum) {
            this.cartNum = cartNum;
        }

        public List<SubjectListBean> getSubjectList() {
            return subjectList;
        }

        public void setSubjectList(List<SubjectListBean> subjectList) {
            this.subjectList = subjectList;
        }

        public List<RecommendTagsBean> getRecommendTags() {
            return recommendTags;
        }

        public void setRecommendTags(List<RecommendTagsBean> recommendTags) {
            this.recommendTags = recommendTags;
        }

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public static class BannerBean {
            private String bannerInfoHeight;
            private String bannerInfoWidth;
            /**
             * link : houpixapp://goods/930
             * imgId : 195962
             */

            private List<BannerInfoBean> bannerList;

            public String getBannerInfoHeight() {
                return bannerInfoHeight;
            }

            public void setBannerInfoHeight(String bannerInfoHeight) {
                this.bannerInfoHeight = bannerInfoHeight;
            }

            public String getBannerInfoWidth() {
                return bannerInfoWidth;
            }

            public void setBannerInfoWidth(String bannerInfoWidth) {
                this.bannerInfoWidth = bannerInfoWidth;
            }

            public List<BannerInfoBean> getBannerList() {
                return bannerList;
            }

            public void setBannerList(List<BannerInfoBean> bannerList) {
                this.bannerList = bannerList;
            }


        }

        public static class BoardBean {
            private String boardInfoHeight;
            private String boardInfoWidth;
            /**
             * imgId : 197532
             * link : houpixapp://topic/1
             */

            private List<BoardListBean> boardList;

            public String getBoardInfoHeight() {
                return boardInfoHeight;
            }

            public void setBoardInfoHeight(String boardInfoHeight) {
                this.boardInfoHeight = boardInfoHeight;
            }

            public String getBoardInfoWidth() {
                return boardInfoWidth;
            }

            public void setBoardInfoWidth(String boardInfoWidth) {
                this.boardInfoWidth = boardInfoWidth;
            }

            public List<BoardListBean> getBoardList() {
                return boardList;
            }

            public void setBoardList(List<BoardListBean> boardList) {
                this.boardList = boardList;
            }

            public static class BoardListBean {
                private String imgId;
                private String link;

                public String getImgId() {
                    return imgId;
                }

                public void setImgId(String imgId) {
                    this.imgId = imgId;
                }

                public String getLink() {
                    return link;
                }

                public void setLink(String link) {
                    this.link = link;
                }
            }
        }

        public static class SubjectListBean {
            /**
             * name : 亿万少女的梦
             * word : 更多
             * link : houpixapp://goods/12
             */
            private List<BannerInfoBean> verticalBannerInfo;  //垂直显示banner
            private TagBean tag;
            private String bannerInfoHeight;
            private String bannerInfoWidth;


            /**
             * id : 937
             * mainPic : 197659
             * price : 111
             * realPrice : 11100
             * unit : 元
             * name : 专用测试1
             * link : houpixapp://goods/937
             */

            private List<GoodsInfoBean> goodsInfo;
            /**
             * id : 11
             * imgId : 193346
             * link : houpixapp://webview/http%3A%2F%2Fwww.baidu.com
             */

            private List<BannerInfoBean> bannerInfo;
            /**
             * title : 我的
             * id : 2
             * imgId : 196986
             * link : houpixapp://goods/23
             */

            private List<BrandInfoBean> brandInfo;

            public TagBean getTag() {
                return tag;
            }

            public void setTag(TagBean tag) {
                this.tag = tag;
            }

            public String getBannerInfoHeight() {
                return bannerInfoHeight;
            }

            public void setBannerInfoHeight(String bannerInfoHeight) {
                this.bannerInfoHeight = bannerInfoHeight;
            }

            public String getBannerInfoWidth() {
                return bannerInfoWidth;
            }

            public void setBannerInfoWidth(String bannerInfoWidth) {
                this.bannerInfoWidth = bannerInfoWidth;
            }

            public List<GoodsInfoBean> getGoodsInfo() {
                return goodsInfo;
            }

            public void setGoodsInfo(List<GoodsInfoBean> goodsInfo) {
                this.goodsInfo = goodsInfo;
            }

            public List<BannerInfoBean> getBannerInfo() {
                return bannerInfo;
            }

            public void setBannerInfo(List<BannerInfoBean> bannerInfo) {
                this.bannerInfo = bannerInfo;
            }

            public List<BrandInfoBean> getBrandInfo() {
                return brandInfo;
            }

            public void setBrandInfo(List<BrandInfoBean> brandInfo) {
                this.brandInfo = brandInfo;
            }

            public List<BannerInfoBean> getVerticalBannerInfo() {
                return verticalBannerInfo;
            }

            public void setVerticalBannerInfo(List<BannerInfoBean> verticalBannerInfo) {
                this.verticalBannerInfo = verticalBannerInfo;
            }

            public static class TagBean {
                private String name;
                private String word;
                private String link;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getWord() {
                    return word;
                }

                public void setWord(String word) {
                    this.word = word;
                }

                public String getLink() {
                    return link;
                }

                public void setLink(String link) {
                    this.link = link;
                }
            }

            public static class GoodsInfoBean {
                private String id;
                private String mainPic;
                private String price;
                private String realPrice;
                private String unit;
                private String name;
                private String link;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getMainPic() {
                    return mainPic;
                }

                public void setMainPic(String mainPic) {
                    this.mainPic = mainPic;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getRealPrice() {
                    return realPrice;
                }

                public void setRealPrice(String realPrice) {
                    this.realPrice = realPrice;
                }

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getLink() {
                    return link;
                }

                public void setLink(String link) {
                    this.link = link;
                }
            }


            public static class BrandInfoBean {
                private String title;
                private String id;
                private String imgId;
                private String link;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getImgId() {
                    return imgId;
                }

                public void setImgId(String imgId) {
                    this.imgId = imgId;
                }

                public String getLink() {
                    return link;
                }

                public void setLink(String link) {
                    this.link = link;
                }
            }
        }

        public static class RecommendTagsBean {
            private String name;
            private String link;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }

        public static class GoodsListBean {
            private String name;
            private String mainPic;
            private String price;
            private String realPrice;
            private String unit;
            private String source;
            private String sales3day;
            private String shippingInfo;
            private String link;
            private String label;
            private String tag;   //判断是否是团购

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMainPic() {
                return mainPic;
            }

            public void setMainPic(String mainPic) {
                this.mainPic = mainPic;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getRealPrice() {
                return realPrice;
            }

            public void setRealPrice(String realPrice) {
                this.realPrice = realPrice;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getSales3day() {
                return sales3day;
            }

            public void setSales3day(String sales3day) {
                this.sales3day = sales3day;
            }

            public String getShippingInfo() {
                return shippingInfo;
            }

            public void setShippingInfo(String shippingInfo) {
                this.shippingInfo = shippingInfo;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }
        }
    }
}
