package com.acs.Test.dto.misc;

/*@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder*/
public class FcResponse {
    private Integer fcId;
    private String fcName;

    public FcResponse(String fcName, Integer fcId) {
        this.fcName = fcName;
        this.fcId = fcId;
    }

    public Integer getFcId() {
        return fcId;
    }

    public void setFcId(Integer fcId) {
        this.fcId = fcId;
    }

    public String getFcName() {
        return fcName;
    }

    public void setFcName(String fcName) {
        this.fcName = fcName;
    }

    @Override
    public String toString() {
        return "FcResponse{" +
                "fcId=" + fcId +
                ", fcName='" + fcName + '\'' +
                '}';
    }
}
