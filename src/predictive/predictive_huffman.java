package predictive;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class predictive_huffman {
    public static void writeImage(int[][] imagePixels,String outPath){

        BufferedImage image = new BufferedImage(imagePixels.length, imagePixels[0].length, BufferedImage.TYPE_INT_RGB);
        for (int y= 0; y < imagePixels.length; y++) {
            for (int x = 0; x < imagePixels[y].length; x++) {
                int value =-1 << 24;
                value= 0xff000000 | (imagePixels[y][x]<<16) | (imagePixels[y][x]<<8) | (imagePixels[y][x]);
                image.setRGB(x, y, value);

            }
        }

        File ImageFile = new File(outPath);
        try {
            ImageIO.write(image, "jpg", ImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }}
    ArrayList<node>table=new ArrayList<>();
    public void compress(int []r,int bits) throws FileNotFoundException {
        ArrayList<node>comp=new ArrayList<>();

        for(int i=1;i<r.length;i++){
            node n=new node();
            n.diff=r[i]-r[i-1];
            comp.add(n);
        }
        int result = (int)Math.pow(2, bits);
        int index = 0;
        int index1=0;
        for (int i = 0; i < comp.size(); i++) {
            if(comp.get(i).diff>comp.get(index).diff){
                index=i;
            }}
        for (int i = 0; i < comp.size(); i++) {
            if(comp.get(i).diff<comp.get(index).diff){
                index1=i;
            }}

        int range=(202+160)/result;


        for(int i=0;i<result;i++){
            node n=new node();
            if(i==0){
                n.low_range=-160;
                n.code=0;
                n.high_range=n.low_range+range;
                n.average=(n.low_range+n.high_range)/2;
            }
            else {

          n.low_range=table.get(i-1).low_range+range;
          n.high_range=table.get(i-1).high_range+range;
          n.average=(n.low_range+n.high_range)/2;

            if(n.average%2!=0){
                n.average=(n.average/2)+1;
            }
            else{
                n.average=(n.average/2);
            }
          n.code=i;
            }
            table.add(n);
        }
        for(int i=1;i<comp.size();i++){
            for(int j=0;j<table.size();j++){
                if(comp.get(i).diff>=table.get(j).low_range&&comp.get(i).diff<=table.get(j).high_range){
                    comp.get(i).quantization=table.get(j).code;
                    break;
                }
            }
        }
        String text="";
        PrintWriter write=new PrintWriter("data.txt");
        text+=r[0];
        write.print(text);
        write.close();
        text="";
        String text1="";
        String text2="";
        String text3="";
        String diff="";
        PrintWriter write1=new PrintWriter("q-1.txt");
        PrintWriter write2=new PrintWriter("low_range.txt");
        PrintWriter write3=new PrintWriter("high_range.txt");
        PrintWriter write4=new PrintWriter("quantizer.txt");
        for(int i=0;i<table.size();i++){
            text1+=table.get(i).average;
            text1+='|';
            text2+=table.get(i).low_range;
            text2+='|';
            text3+=table.get(i).high_range;
            text3+='|';
        }
        for(int i=1;i<comp.size();i++){
            text+=comp.get(i).quantization;
            text+='|';
        }
        write1.print(text1);
        write1.close();
        write2.print(text2);
        write2.close();
        write3.print(text3);
        write3.close();
        write4.print(text);
        write4.close();


    }
    public void decompress() throws FileNotFoundException {
        ArrayList<node> table = new ArrayList<>();
        ArrayList<node> comp = new ArrayList<>();
        File f = new File("q-1.txt");
        File f_d = new File("data.txt");
        File f1 = new File("low_range.txt");
        File f2 = new File("high_range.txt");
        File f3 = new File("quantizer.txt");
        String text = "";
        String text1 = "";
        String text2 = "";
        String text3 = "";
        String d="";
        try (Scanner in = new Scanner(f3)) {


            while (in.hasNextLine()) {
                d += in.nextLine();
            }
        }
        String r="";
        for(int i=0;i<d.length();i++){
            if(d.charAt(i)=='|'){

                int y=Integer.parseInt(r);
                node n=new node();
                n.diff=y;
                comp.add(n);
                r="";
            }
            else{
                r+=d.charAt(i);
            }
        }
        try (Scanner in = new Scanner(f_d)) {


            while (in.hasNextLine()) {
                text += in.nextLine();
            }
        }
        int data=Integer.parseInt(text);
        text="";
        try (Scanner in = new Scanner(f_d)) {


            while (in.hasNextLine()) {
                text += in.nextLine();
            }
        }

        try (Scanner in = new Scanner(f)) {
            while (in.hasNextLine()) {
                text1 += in.nextLine();
            }
        }
        try (Scanner in = new Scanner(f1)) {


            while (in.hasNextLine()) {
                text2 += in.nextLine();
            }
        }
        try (Scanner in = new Scanner(f2)) {


            while (in.hasNextLine()) {
                text3 += in.nextLine();
            }
        }
        String t = "";
        for (int i = 0; i < text1.length(); i++) {
            if (text1.charAt(i) == '|') {
                int x = Integer.parseInt(t);


                node b = new node();
                b.average = x;
                table.add(b);
                t = "";

            } else {
                t += text1.charAt(i);
            }}

            int count =0;
                t="";
            for(int i=0;i<text2.length();i++){
                if(text2.charAt(i)=='|'){
                    int x=Integer.parseInt(t);

                    table.get(count).low_range=x;
                    count++;
                    t="";
                }
                else{
                    t+=text2.charAt(i);
                }
        }
        count=0;
        t="";
        for(int i=0;i<text3.length();i++){
            if(text3.charAt(i)=='|'){
                int x=Integer.parseInt(t);

                table.get(count).high_range=x;
                count++;
                t="";
            }
            else{
                t+=text3.charAt(i);
            }
        }

        for(int i=0;i<table.size();i++){
            table.get(i).code=i;
        }
        int []p=new int [125*125] ;
        int [][]pixels=new int[125][125];
        p[0]=data;
        for(int i=0;i<comp.size();i++){
            for(int j=0;j<table.size();j++){
                if(comp.get(i).diff>table.get(j).low_range&&comp.get(i).diff<table.get(j).high_range ){
                    comp.get(i).quantization=table.get(j).average;
                }
                else if(comp.get(i).diff>table.get(j).high_range){
                    comp.get(i).quantization=comp.get(i).high_range;
                }
            }
        }
        int c=1;
        for(int i=1;i<comp.size();i++){
            p[c]=p[c-1]+comp.get(i).quantization;
                c++;
        }
        c=0;
for(int row=0;row<125;row++){
            for(int col=0;col<125;col++){
            pixels[row][col]=p[c];
            c++;
            }
}

        for(int row=0;row<125;row++){
            for(int col=0;col<125;col++){
              System.out.println(pixels[row][col]);
            }}

writeImage(pixels,"cameraMan.jpg");

    }





    public static void main(String[] args) throws FileNotFoundException {
        predictive_huffman v=new predictive_huffman();


    }
}
