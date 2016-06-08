package com.zaizi.automation.alfresco.core.screenCast;

import java.awt.*;
import java.io.File;

import org.monte.media.Format;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;


import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;


public class VideoReord {
	private ScreenRecorder screenRecorder;
	
	public void startRecording() throws Exception
    {    
           File file = new File("/Users/mketheeswaran/Desktop/Reports/videos");
                         
           Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
           int width = screenSize.width;
           int height = screenSize.height;
                          
           Rectangle captureSize = new Rectangle(0,0, width, height);
                          
         GraphicsConfiguration gc = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .getDefaultConfiguration();
         	
         
        this.screenRecorder = new SpecializedScreenRecorder(gc, captureSize,
            new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey,MIME_QUICKTIME),
            new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_QUICKTIME_ANIMATION,
                 CompressorNameKey, COMPRESSOR_NAME_QUICKTIME_ANIMATION,
                 DepthKey, 24, FrameRateKey, Rational.valueOf(15),
                 QualityKey, 1.0f,
                 KeyFrameIntervalKey, 15 * 60),
            new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black",
                 FrameRateKey, Rational.valueOf(30)),
            null, file,"MyVideo");
       this.screenRecorder.start();
    
    }

    public void stopRecording() throws Exception
    {
      this.screenRecorder.stop();
    }
}

