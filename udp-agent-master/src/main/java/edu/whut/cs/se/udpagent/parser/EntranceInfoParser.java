package edu.whut.cs.se.udpagent.parser;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import entranceinfo.EntranceInfo;
import org.springframework.stereotype.Component;

@Component
public class EntranceInfoParser {
    public void parseEntranceInfo() {}

    public void setEntranceInfo(String jsonStr) {
        JSONObject entraceInfoJson = JSON.parseObject(jsonStr);
        setOutside(entraceInfoJson.getInteger("outside"));
        setxCoordinate(entraceInfoJson.getJSONObject("explosion").getDouble("x"));
        setyCoordinate(entraceInfoJson.getJSONObject("explosion").getDouble("y"));
        setWeaponType(entraceInfoJson.getJSONObject("explosion").getInteger("weapon_type"));
        setEquivalent(entraceInfoJson.getJSONObject("explosion").getDouble("equivalent"));
        setStructure(entraceInfoJson.getDouble("structure"));
        setDevice(entraceInfoJson.getDouble("device"));
        setOverall(entraceInfoJson.getDouble("overall"));
    }

    private int outside;            // 外部安全状态感知

    // 爆源信息
    private double xCoordinate;
    private double yCoordinate;
    private int weaponType;
    private double equivalent;

    // 结构损伤
    private double structure;

    // 设备损伤
    private double device;

    // 总体性能评价
    private double overall;

    public int getOutside() {
        return outside;
    }

    public void setOutside(int outside) {
        this.outside = outside;
    }

    public double getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public double getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public int getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(int weaponType) {
        this.weaponType = weaponType;
    }

    public double getEquivalent() {
        return equivalent;
    }

    public void setEquivalent(double equivalent) {
        this.equivalent = equivalent;
    }

    public double getStructure() {
        return structure;
    }

    public void setStructure(double structure) {
        this.structure = structure;
    }

    public double getDevice() {
        return device;
    }

    public void setDevice(double device) {
        this.device = device;
    }

    public double getOverall() {
        return overall;
    }

    public void setOverall(double overall) {
        this.overall = overall;
    }

    @Override
    public String toString() {
        return "EntranceInfoParser{" +
                "outside=" + outside +
                ", xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                ", weaponType=" + weaponType +
                ", equivalent=" + equivalent +
                ", structure=" + structure +
                ", device=" + device +
                ", overall=" + overall +
                '}';
    }
}
