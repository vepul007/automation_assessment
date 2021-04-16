package utility;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class Utility {

    public static String getScreenshot(WebDriver driver){
        TakesScreenshot ts = (TakesScreenshot)  driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir")+"/output/screenshots/"+System.currentTimeMillis()+".png";
        File destination = new File(path);
        try{
            FileUtils.copyFile(src,destination);
        }catch(IOException e){
            System.out.println("Capture failed" +e.getMessage());
        }
        return path;
    }
}
