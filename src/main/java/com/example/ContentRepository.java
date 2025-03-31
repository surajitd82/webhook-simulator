package com.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ContentRepository {

    public static void setRepoList(List<String> repoList) {
        ContentRepository.repoList = repoList;
    }

    public static void addToRepoList(String repoData) {
        if(repoList==null) {
            repoList = new LinkedList<>();
            System.out.println("Repository Created..");
        }
        repoList.add(repoData);
    }

    public static List<String> getRepoList() {
        return repoList;
    }

    @Override
    public String toString() {
        return "ContentRepository{}";
    }

    public static List<String> repoList;

}
