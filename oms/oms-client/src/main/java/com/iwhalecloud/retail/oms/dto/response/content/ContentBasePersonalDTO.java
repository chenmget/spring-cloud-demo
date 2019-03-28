package com.iwhalecloud.retail.oms.dto.response.content;

import com.iwhalecloud.retail.oms.dto.*;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import java.util.List;


/**
 * ContentBase
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "内容详情个性化信息")
public class ContentBasePersonalDTO implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;

  	//目录信息
  	private List<CataLogDTO> cataLogs;
	//标签
	private List<TagDTO> tags;
	//内容标签
	private List<ContentTagDTO> contentTags;
	//内容素材
	private List<ContentMaterialDTO> contentMaterials;
	//软文
    private List<ContentTextDTO> contentTexts;
	//单图片内容
	private List<ContentPicDTO> contentPics;
	//轮播图内容
	private List<ContentOrderpicDTO> contentOrderpics;
	//推广内容
	private List<ContentPicsetDTO> contentPicsets;
	//视频二级内容表
	private List<ContentVediolv2DTO> contentVediolv2s;
	//视频内容
	private List<ContentVideosDTO> contentVedios;

	public List<CataLogDTO> getCataLogs() {
		return cataLogs;
	}

	public void setCataLogs(List<CataLogDTO> cataLogs) {
		this.cataLogs = cataLogs;
	}

	public List<TagDTO> getTags() {
		return tags;
	}

	public void setTags(List<TagDTO> tags) {
		this.tags = tags;
	}

	public List<ContentTagDTO> getContentTags() {
		return contentTags;
	}

	public void setContentTags(List<ContentTagDTO> contentTags) {
		this.contentTags = contentTags;
	}

	public List<ContentMaterialDTO> getContentMaterials() {
		return contentMaterials;
	}

	public void setContentMaterials(List<ContentMaterialDTO> contentMaterials) {
		this.contentMaterials = contentMaterials;
	}

	public List<ContentTextDTO> getContentTexts() {
		return contentTexts;
	}

	public void setContentTexts(List<ContentTextDTO> contentTexts) {
		this.contentTexts = contentTexts;
	}

	public List<ContentPicDTO> getContentPics() {
		return contentPics;
	}

	public void setContentPics(List<ContentPicDTO> contentPics) {
		this.contentPics = contentPics;
	}

	public List<ContentOrderpicDTO> getContentOrderpics() {
		return contentOrderpics;
	}

	public void setContentOrderpics(List<ContentOrderpicDTO> contentOrderpics) {
		this.contentOrderpics = contentOrderpics;
	}

	public List<ContentPicsetDTO> getContentPicsets() {
		return contentPicsets;
	}

	public void setContentPicsets(List<ContentPicsetDTO> contentPicsets) {
		this.contentPicsets = contentPicsets;
	}

	public List<ContentVediolv2DTO> getContentVediolv2s() {
		return contentVediolv2s;
	}

	public void setContentVediolv2s(List<ContentVediolv2DTO> contentVediolv2s) {
		this.contentVediolv2s = contentVediolv2s;
	}

	public List<ContentVideosDTO> getContentVedios() {
		return contentVedios;
	}

	public void setContentVedios(List<ContentVideosDTO> contentVedios) {
		this.contentVedios = contentVedios;
	}
}
