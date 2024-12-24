package cn.edu.whut.dhdas.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Random;

// 监测断面类
class Section {
    float a;    // 压电式加速度传感器
    float v;    // 振动速度传感器
    float ap;   // 空气超压传感器
    float s;    // 半导体应变片
    float t;    // 光纤温度传感器
    float p;    // 光纤压力传感器
    float oa;   // 光纤加速度传感器
    float os;   // 光线应变传感器
}

class Door {
    float dv;   // 压电式加速度传感器
    float dp;   // 光纤压力传感器
    float ds1;  // 半导体应变片1
    float ds2;  // 半导体应变片2
    float dov;  // 光纤加速度传感器
    float dos1; // 光纤应变传感器1
    float dos2; // 光纤应变传感器2
}

public class DataColumn {
    @Getter
    @Setter
    private float time;
    @Getter
    @Setter
    private float channel0;
    @Getter
    @Setter
    private float channel1;

    // 监测断面
    private Section[] sections = new Section[5];

    // 地面传感器
    private float[] ua = new float[8];              // 压电式加速度传感器
    private float[] uoa = new float[8];             // 光纤加速度传感器

    // 防护门
    private Door[] doors = new Door[2];

    Random random = new Random();

    public DataColumn(float time, List<Float> resut) {
//        this.setTime(resut.get(0));
        this.setTime(time);
        this.setChannel0(resut.get(0));
        this.setChannel1(resut.get(1));
    }

    // 传感器数值随机
    public DataColumn(float time) {
        this.setTime(time);
        this.setSections();
        this.setGrounds();
        this.setDoors();
    }

    public void setSections() {
        for (int i = 0; i < sections.length; ++i) {
            sections[i].a = random.nextFloat() * 50;
            sections[i].v = random.nextFloat() * 40;
            sections[i].ap = random.nextFloat() / 2;
            sections[i].s = random.nextFloat() * 2000;
            sections[i].t = random.nextFloat() * 800;
            sections[i].p = random.nextFloat() / 2;
            sections[i].oa = random.nextFloat() * 50;
            sections[i].os = random.nextFloat() * 2000;
        }
    }

    public void setGrounds() {
        for (int i = 0; i < ua.length; ++i) {
            ua[i] = random.nextFloat() * 50;
        }
        for (int i = 0; i < uoa.length; ++i) {
            uoa[i] = random.nextFloat() * 50;
        }
    }

    public void setDoors() {
        for (int i = 0; i < doors.length; ++i) {
            doors[i].dv = random.nextFloat() * 50;
            doors[i].dp = random.nextFloat() * 50;
            doors[i].ds1 = random.nextFloat() * 50;
            doors[i].ds2 = random.nextFloat() * 50;
            doors[i].dov = random.nextFloat() * 50;
            doors[i].dos1 = random.nextFloat() * 50;
            doors[i].dos2 = random.nextFloat() * 50;
        }
    }

    @Override
    public String toString() {
        // 注意保证字段顺序
//        StringBuffer sb = new StringBuffer();
//        sb.append(this.getTime()).append('\t').
//                append(this.getChannel0()).append('\t').
//                append(this.getChannel1()).
//                append('\n');
//        return sb.toString();


        StringBuffer sb = new StringBuffer();
        sb.append(this.getTime()).append('\t');

        // 断面
        for (int i = 0; i < sections.length; ++i) {
            sb.append(sections[i].a).append('\t').
                    append(sections[i].v).append('\t').
                    append(sections[i].ap).append('\t').
                    append(sections[i].s).append('\t').
                    append(sections[i].t).append('\t').
                    append(sections[i].p).append('\t').
                    append(sections[i].oa).append('\t').
                    append(sections[i].os).append('\t');
        }

        // 地面
        for (int i = 0; i < ua.length; ++i) {
            sb.append(ua[i]).append('\t');
        }
        for (int i = 0; i < uoa.length; ++i) {
            sb.append(uoa[i]).append('\t');
        }

        // 防护门
        for (int i = 0; i < doors.length; ++i) {
            sb.append(doors[i].dv).append('\t').
                    append(doors[i].dp).append('\t').
                    append(doors[i].ds1).append('\t').
                    append(doors[i].ds2).append('\t').
                    append(doors[i].dov).append('\t').
                    append(doors[i].dos1).append('\t').
                    append(doors[i].dos2).append('\t');
        }

        return sb.toString();
    }
}
