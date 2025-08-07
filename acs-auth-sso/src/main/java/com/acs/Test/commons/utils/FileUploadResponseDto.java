package com.acs.Test.commons.utils;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FileUploadResponseDto {

  private String fileUrl;

  private String filePath;

  private String fileName;

  private String fileExtension;

  @JsonIgnore
  private File file;

  private String message;

  public FileUploadResponseDto() {
    super();
  }

  public FileUploadResponseDto(String fileUrl, String filePath, String fileName,
      String fileExtension, File file, String message) {
    super();
    this.fileUrl = fileUrl;
    this.filePath = filePath;
    this.fileName = fileName;
    this.fileExtension = fileExtension;
    this.file = file;
    this.message = message;
  }

  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileExtension() {
    return fileExtension;
  }

  public void setFileExtension(String fileExtension) {
    this.fileExtension = fileExtension;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  @Override
  public String toString() {
    return "FileUploadResponseDto [fileUrl=" + fileUrl + ", filePath=" + filePath + ", fileName="
        + fileName + ", fileExtension=" + fileExtension + ", file=" + file + ", message=" + message
        + "]";
  }

}
