package fr.istic.m1.fstorm.modules;

import java.util.Arrays;
import java.util.List;

import fr.istic.m1.fstorm.beans.StormComponent;
import fr.istic.m1.fstorm.beans.StormComponentType;

public class TestSpout {
    List<StormComponent> list;
    public TestSpout(List<StormComponent> l){
        list=l;
    }
    public void compute() {
        
        GenerateJavaSpout gen = new GenerateJavaSpout("packageName");
        for(StormComponent pouet : list){
        gen.Execute(pouet);
        System.out.println("----------------------");
        }

    }

}