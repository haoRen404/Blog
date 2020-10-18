package com.lcy.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity // 这个注释标识这是一个实体类（已被废弃）
@Table(name = "t_blog") // @Table 类名和数据表名不一致时，用这个注释指出数据表
public class Blog {

    @Id // 指定这是id
    @GeneratedValue //// 提供了主键的生成策略
    private Long id;

    private String title;// 标题

    @Basic(fetch = FetchType.LAZY)  // @Basic 是实体类与数据库字段映射时最简单的类型
    @Lob // @Lob 指定持久属性或字段应作为大对象持久保存到数据库支持的大对象类型。
    private String content;// 内容

    private String firstPicture;// 内容
    private String flag;// 标记
    private Integer views;// 浏览次数
    private boolean appreciation;// 赞赏开启
    private boolean shareStatement;// 转载声明
    private boolean commentabled;// 评论开启
    private boolean published;// 发布
    private boolean recommend;// 推荐

    @Temporal(TemporalType.TIMESTAMP)   // Date类型进行格式化，完整的时间“yyyy-MM-dd hh:MM:ss”
    private Date createTime;// 创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;// 更新时间

    @ManyToOne // 设置实体关系，多对一
    private Type type;

    @ManyToMany(cascade = {CascadeType.PERSIST}) // 设置实体关系，多对多
    private List<Tag> tags = new ArrayList<>();


    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    @Transient  // 标识是普通的属性，不进行数据库操作
    private String tagIds;

    private String description;

    public Blog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    // 初始化
    public void init() {
        this.tagIds = tagsToIds(this.getTags());// getTags()获取到标签列表，tagsToIds()将标签id拼接成字符串
    }

    // 拼接标签id成字符串
    private String tagsToIds(List<Tag> tags) {
        // isEmpty()用来判断某容器中是否存在元素
        if (!tags.isEmpty()) {// 如果标签不存在
            // append是StringBuffer的方法，表示追加字符串
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }


    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", tags=" + tags +
                ", user=" + user +
                ", comments=" + comments +
                ", tagIds='" + tagIds + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
