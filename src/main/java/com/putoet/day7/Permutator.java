package com.putoet.day7;

import java.util.ArrayList;
import java.util.List;

public class Permutator<T> {
    public List<List<T>> permute(List<T> source) {
        List<List<T>> list = new ArrayList<>();
        permuteHelper(list, new ArrayList<>(), source);
        return list;
    }

    private void permuteHelper(List<List<T>> list, List<T> resultList, List<T> source){
        // Base case
        if(resultList.size() == source.size()){
            list.add(new ArrayList<T>(resultList));
        }
        else{
            for(int i = 0; i < source.size(); i++){

                if(resultList.contains(source.get(i)))
                {
                    // If element already exists in the list then skip
                    continue;
                }
                // Choose element
                resultList.add(source.get(i));
                // Explore
                permuteHelper(list, resultList, source);

                // Unchoose element
                resultList.remove(resultList.size() - 1);
            }
        }
    }
}
