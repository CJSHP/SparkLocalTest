package com.shp.demo;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;
import java.util.Arrays;
import java.util.List;

public class WordCount {

    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setAppName("word count").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        List<String> data = Arrays.asList("how to sing abc song", "to be or not to be", "that is a question",
                "sing a song with drake");
        sc.parallelize(data)
                .flatMap((FlatMapFunction<String, String>) s -> Arrays.asList(s.split(" ")).iterator())
                .mapToPair((PairFunction<String, String, Integer>) s -> new Tuple2<>(s, 1))
                .reduceByKey(Integer::sum)
                .foreach(p -> System.out.println(p._1 + ", " + p._2));
    }
}