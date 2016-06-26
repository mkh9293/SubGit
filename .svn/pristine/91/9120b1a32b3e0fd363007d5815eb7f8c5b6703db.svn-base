package subgit.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.*;


public class CodeViewUtil {

	static String[] code = null;
	/* 파일 경로에서 파일명 가져오기 */ 
	public static String getFileName(String filePath){
		String[] fileName = filePath.split("/");
		String[] fileName2 = new String[2];
		int size = fileName.length;
		fileName2 = fileName[size-1].split("]");
		return fileName2[0];
	}
		
	/* 파일 확장자 가져오기 */
	public static String getFileExtension(String fileName) {
		int lastIndexOf = fileName.lastIndexOf(".");
		if (lastIndexOf == -1) {
			return "";
		}
		return fileName.substring(lastIndexOf+1);
	}
	

	/* 확장자 비교 */ 
	public static boolean isCodeName(String str){     
		str = str.toLowerCase();
		if(str.endsWith(".c") || str.endsWith(".h")|| str.endsWith(".ino")
				|| str.endsWith(".java")|| str.endsWith(".py")|| str.endsWith(".cpp") || str.endsWith(".hpp")
				|| str.endsWith(".html")|| str.endsWith(".css")|| str.endsWith(".pl") || str.endsWith(".r")
				|| str.endsWith(".sql")|| str.endsWith(".php")|| str.endsWith(".cs") || str.endsWith(".scala")
				|| str.endsWith("makefile")|| str.endsWith(".bas")|| str.endsWith(".asm") || str.endsWith(".jsp")
				|| str.endsWith("pym")|| str.endsWith(".less")|| str.endsWith(".rh") || str.endsWith(".cc")
				|| str.endsWith("cp")|| str.endsWith(".mf")|| str.endsWith(".rh") || str.endsWith(".meta")
				|| str.endsWith(".m")|| str.endsWith(".wiki")|| str.endsWith(".lua") || str.endsWith(".json")
				|| str.endsWith(".rb")|| str.endsWith(".txt")|| str.endsWith(".js") || str.endsWith(".do")
				|| str.endsWith(".aspx")|| str.endsWith(".tcl")|| str.endsWith(".el") || str.endsWith(".cxx")
				|| str.endsWith(".iml")|| str.endsWith(".htm")|| str.endsWith(".rhtml") || str.endsWith(".pde")
				|| str.endsWith("license")|| str.endsWith(".gradle")|| str.endsWith("notice") || str.endsWith(".bat")
				|| str.endsWith(".gitignore")|| str.endsWith(".asp")|| str.endsWith(".ss") || str.endsWith(".properties")
				|| str.endsWith(".xml")|| str.endsWith(".md") || str.endsWith(".log")|| str.endsWith(".pom"))
			return true;
		return false;
	}
	/* 이미지 확장자 검사 */
	public static boolean isImageName(String filename){ 
		filename = filename.toUpperCase();

		if(filename.endsWith(".ANI") || filename.endsWith(".BMP") || filename.endsWith(".CAL")
				|| filename.endsWith(".CAL") || filename.endsWith(".FAX") || filename.endsWith(".GIF")
				|| filename.endsWith(".IMG") || filename.endsWith(".JPE") || filename.endsWith(".JPEG")
				|| filename.endsWith(".JPG") || filename.endsWith(".MAC") || filename.endsWith(".PBM")
				|| filename.endsWith(".PCD") || filename.endsWith(".PCX") || filename.endsWith(".PCT")
				|| filename.endsWith(".PGM") || filename.endsWith(".PNG") || filename.endsWith(".PPM")
				|| filename.endsWith(".PSD") || filename.endsWith(".RAS") || filename.endsWith(".TGA")
				|| filename.endsWith(".TIF") || filename.endsWith(".TIFF") || filename.endsWith(".WMF")){
			return true;
		}
		return false;
	}
	/* 코드 확장자 불일치시 뷰어 불가능 */
	public void onlyViewCode(){
			
			System.out.println("이것은 볼 수 없는 형식의 파일입니다");
		
	}
	/* 파일 경로를 받아 저장된 코드 배열을 리턴하는 메소드 */
	
	public static List<String> codeOutput(String filePath) throws IOException{
		List<String> contents =new ArrayList<String>();
		File file = new File(filePath); 
		if(file.exists()==true){
		BufferedReader in = new BufferedReader(new FileReader(filePath));
		String s=null;
		while((s = in.readLine())!=null){			
			contents.add(s);
		}
		}
		return contents; 
		
	}
	
	/* 이미지 형식은 뷰어가 불가능 */
	public void notViewImage(boolean isImage){
		if(isImage==true){
			System.out.println("이것은 볼 수 없는 형식의 파일입니다");
		}
	}
}
