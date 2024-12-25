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
    private float channel1;
    @Getter
    @Setter
    private float channel2;

    // 测试代码
    private float channel3;
    private float channel4;
    private float channel5;
    private float channel6;
    private float channel7;
    private float channel8;
    private float channel9;
    private float channel10;
    private float channel11;
    private float channel12;
    private float channel13;
    private float channel14;
    private float channel15;
    private float channel16;
    private float channel17;


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
        this.setChannel1(resut.get(0));
        this.setChannel2(resut.get(1));
    }

    // 传感器数值随机
//    public DataColumn(float time) {
//        this.setTime(time);
//        this.setSections();
//        this.setGrounds();
//        this.setDoors();
//    }

    // 测试代码，随机当前的十七列数据
    public DataColumn(float time) {
        this.setTime(time);
        this.channel1 = random.nextFloat() * 4 - 2;
        this.channel2 = random.nextFloat() * 2 - 1;
        this.channel3 = random.nextFloat() * 2 - 1;
        this.channel4 = random.nextFloat() * 2 - 1;
        this.channel5 = random.nextFloat() * 10 - 5;
        this.channel6 = random.nextFloat() * 4 - 2;
        this.channel7 = random.nextFloat() * 2 - 1;
        this.channel8 = random.nextFloat() * 2 - 1;
        this.channel9 = random.nextFloat() * 40000000 - 20000000;
        this.channel10 = random.nextFloat() * 1000;
        this.channel11 = random.nextFloat() * 40 - 20;
        this.channel12 = random.nextFloat() * 20 - 10;
        this.channel13 = random.nextFloat() * 10 - 5;
        this.channel14 = random.nextFloat() * 310 - 10;
        this.channel15 = random.nextFloat() * 610 - 10;
        this.channel16 = random.nextFloat() * 10 - 5;
        this.channel17 = random.nextFloat() * 40- 20;

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
        StringBuffer sb = new StringBuffer();
        sb.append(this.getTime()).append('\t');

        // 注意保证字段顺序
//        sb.append(this.getTime()).append('\t').
//                append(this.getChannel1()).append('\t').
//                append(this.getChannel2()).
//                append('\n');
//        return sb.toString();

//        // 断面
//        for (int i = 0; i < sections.length; ++i) {
//            sb.append(sections[i].a).append('\t').
//                    append(sections[i].v).append('\t').
//                    append(sections[i].ap).append('\t').
//                    append(sections[i].s).append('\t').
//                    append(sections[i].t).append('\t').
//                    append(sections[i].p).append('\t').
//                    append(sections[i].oa).append('\t').
//                    append(sections[i].os).append('\t');
//        }
//
//        // 地面
//        for (int i = 0; i < ua.length; ++i) {
//            sb.append(ua[i]).append('\t');
//        }
//        for (int i = 0; i < uoa.length; ++i) {
//            sb.append(uoa[i]).append('\t');
//        }
//
//        // 防护门
//        for (int i = 0; i < doors.length; ++i) {
//            sb.append(doors[i].dv).append('\t').
//                    append(doors[i].dp).append('\t').
//                    append(doors[i].ds1).append('\t').
//                    append(doors[i].ds2).append('\t').
//                    append(doors[i].dov).append('\t').
//                    append(doors[i].dos1).append('\t').
//                    append(doors[i].dos2).append('\t');
//        }

        // 测试
        sb.append(channel1).append('\t').
                append(channel2).append('\t').
                append(channel3).append('\t').
                append(channel4).append('\t').
                append(channel5).append('\t').
                append(channel6).append('\t').
                append(channel7).append('\t').
                append(channel8).append('\t').
                append(channel9).append('\t').
                append(channel10).append('\t').
                append(channel11).append('\t').
                append(channel12).append('\t').
                append(channel13).append('\t').
                append(channel14).append('\t').
                append(channel15).append('\t').
                append(channel16).append('\t').
                append(channel17).append('\t');

        sb.append('\n');

        return sb.toString();
    }
}
