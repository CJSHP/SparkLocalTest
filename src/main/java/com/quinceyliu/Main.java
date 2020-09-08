package com.quinceyliu;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setAppName("count").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        List<String> data = Arrays.asList("Google Bye GoodBye Hadoop code", "Java code Bye");
        sc.parallelize(data)
                .flatMap((FlatMapFunction<String, String>) s -> Arrays.asList(s.split(" ")).iterator())
                .mapToPair((PairFunction<String, String, Integer>) s -> new Tuple2<>(s, 1))
                .reduceByKey(Integer::sum)
                .foreach(p -> System.out.println(p._1 + ", " + p._2));
    }
}