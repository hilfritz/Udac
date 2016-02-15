import static java.lang.Math.floor;
import java.io.*;
import java.util.*;
/**
* See http://petrnohejl.github.io/Android-Cheatsheet-For-Graphic-Designers/
* @since February 15, 2016
* @author Hilfritz P. Camallere
*/
public class DimensionGenerator{
	/**
	* <dimen name="activity_vertical_margin">16dp</dimen>
	*/

	String header ="<resources>";
	String singleDimenA = "<dimen name='";
	String singleDimenB = "'>";
	String singleDimenC1 = "dp";
	String singleDimenC2 = "sp";
	String singleDimenD = "</dimen>";
	String footer ="</resources>";

	public static final double LDPI = 0.75;
	public static final double MDPI = 1;
	public static final double HDPI = 1.5;
	public static final double XHDPI = 2;
	public static final double XXHDPI = 3;
	public static final double XXXHDPI = 4;

	public static void main(String[] args){
		DimensionGenerator dg = new DimensionGenerator();
		int max = 1000;
		dg.log("LDPI");
		//dg.log(dg.generateDimensionString(LDPI, dg, max));
		dg.createFile(dg.generateDimensionString(LDPI, dg, max),LDPI, dg);

		dg.log("MDPI");
		dg.createFile(dg.generateDimensionString(MDPI, dg, max),MDPI, dg);

		dg.log("HDPI");
		dg.createFile(dg.generateDimensionString(HDPI, dg, max),HDPI, dg);

		dg.log("XHDPI");
		dg.createFile(dg.generateDimensionString(XHDPI, dg, max),XHDPI, dg);

		dg.log("XXHDPI");
		dg.createFile(dg.generateDimensionString(XXHDPI, dg, max),XXHDPI, dg);

		dg.log("XXXHDPI");
		dg.createFile(dg.generateDimensionString(XXXHDPI, dg, max),XXXHDPI, dg);

	}

	private void createFile(String contents, double mode, DimensionGenerator dg){
		String fileName = dg.getFileName(mode)+"-dimens.xml";
		try(  
			PrintWriter out = new PrintWriter( fileName )  ){
    		out.println( contents );
		}catch(Exception e){
			dg.log("ERROR!");
		}
	}

private String getFileName(double mode){
	String retVal = "";

	if (mode == LDPI){
		retVal ="ldpi";
	}else if (mode == MDPI){
		retVal ="mdpi";
	}else if (mode == HDPI){
		retVal ="hdpi";
	}else if (mode == XHDPI){
		retVal ="xhdpi";
	}else if (mode == XXHDPI){
		retVal ="xxhdpi";
	}else if (mode == XXXHDPI){
		retVal ="xxxhdpi";
	}
	return retVal;
}
	

	private String generateDimensionString(double mode, DimensionGenerator dg, int x ){
		String temp = "";
		double z = 0;
		int p = 0;
		for (int y = 0 ; y<x; y++){
			double d = y * mode;

			//z = Math.floor(y*mode);
			z = Math.floor(d);
			p = (int)z;
			temp += "\n    "+dg.getMdpiDpDimensionString("dimen"+y, ""+p);
			//temp += "\n"+dg.getMdpiDpDimensionString("dimen"+y, ""+z);
		}

		return header+"\n"+temp+"\n"+footer;
	}

	public String getMdpiDpDimensionString(String x, String y){
		return singleDimenA+x+singleDimenC1+singleDimenB+y+singleDimenC1+singleDimenD;
	}

	public void log(String str){
		System.out.println(str);
	}
}