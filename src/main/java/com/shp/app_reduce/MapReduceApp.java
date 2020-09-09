package com.shp.app_reduce;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MapReduceApp {

    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setAppName("map reduce app").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        List<AppMessage> data = Arrays.asList(
                new AppMessage("app1", 1, 3),
                new AppMessage("app2", 2, 4),
                new AppMessage("app1", 3, 5),
                new AppMessage("app2", 3, 5),
                new AppMessage("app1", 10, 12),
                new AppMessage("app2", 15, 17)
        );
        sc.parallelize(data)
                .mapToPair((PairFunction<AppMessage, String, List<Pair>>) s ->
                        new Tuple2<>(s.getAppName(),
                                Collections.singletonList(new Pair(s.getStartTime(), s.getEndTime()))))
                .reduceByKey((a, b) -> {
                    if (a.isEmpty()) {
                        return b;
                    } else if (b.isEmpty()) {
                        return a;
                    }
                    List<Pair> list = new ArrayList<>();
                    int leftBegin = 1, rightBegin = 0, begin = a.get(0).first, end = a.get(0).second;
                    while (leftBegin < a.size() && rightBegin < b.size()) {
                        Pair current;
                        if (a.get(leftBegin).compareTo(b.get(rightBegin)) <= 0) {
                            current = a.get(leftBegin++);
                        } else {
                            current = b.get(rightBegin++);
                        }
                        if (end < current.first) {
                            list.add(new Pair(begin, end));
                            begin = current.first;
                        }
                        end = current.second;
                    }
                    while (leftBegin < a.size()) {
                        Pair current = a.get(leftBegin++);
                        if (end < current.first) {
                            list.add(new Pair(begin, end));
                            begin = current.first;
                        }
                        end = current.second;
                    }
                    while (rightBegin < b.size()) {
                        Pair current = b.get(rightBegin++);
                        if (end < current.first) {
                            list.add(new Pair(begin, end));
                            begin = current.first;
                        }
                        end = current.second;
                    }
                    list.add(new Pair(begin, end));
                    return list;
                })
                .foreach(p -> System.out.println(p._1 + ", " + p._2));
    }
}