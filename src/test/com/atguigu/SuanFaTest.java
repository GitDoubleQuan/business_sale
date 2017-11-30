package com.atguigu;

/**
 * Created by Shuangquan.Xu on 2017/11/30.
 */
public class SuanFaTest {

    int[] array = {1,2,3,4,5,6,2,2,8,8,1};

    public void test(){
        for(int i = 0; i < array.length; i++){


        }


    }

    public int[] bubble(int[] data){
        for(int i=0;i<data.length;i++){
            for(int j=0;j<data.length-1-i;j++){
                if(data[j]>data[j+1]){   //如果后一个数小于前一个数交换
                    int tmp=data[j];
                    data[j]=data[j+1];
                    data[j+1]=tmp;
                }
            }
        }
        return data;
    }
}
