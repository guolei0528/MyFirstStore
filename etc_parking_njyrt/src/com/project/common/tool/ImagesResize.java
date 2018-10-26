package com.project.common.tool;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/** 图片工具类，完成图片的截取
 * 
 * @author Beau Virgill 
 */
public class ImagesResize
{
    private static Log log = LogFactory.getLog(ImagesResize.class);

    BufferedImage bufImage; // 原始图片
    int width; // 缩放的宽度
    int height; // 缩放的高度

    public ImagesResize()
    {
        // TODO Auto-generated constructor stub
    }

    public ImagesResize(String srcPath, int width, int height)
    {
        this.width = width;
        this.height = height;
        try
        {
            this.bufImage = ImageIO.read(new File(srcPath));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /** 实现图像的等比缩放和缩放后的截取，如果高度的值和宽度一样，则缩放按设置的值缩放 (只控制宽度的大小，高度的值设置不生效(只有高度的值和宽度的一样才生效)， 高度自动按比例缩放；如果缩放的图片小于你设置的值则保存原图大小)
     * 
     * @param inFilePath
     *            要缩放图片文件的路径
     * @param outFilePath
     *            缩放后保存图片输出的路径
     * @param width
     *            要截取宽度
     * @param hight
     *            要截取的高度
     * @throws Exception
     * width 或者 height 为负数时(-1)的时,进行缩放 
     *  
     *  */
    

    public static void zoomOutImage(String inFilePath, String outFilePath, int width, int hight, boolean smooth)
            throws Exception
    {
//        int maxHight = 500; // 设置最大的图片高度;

        File file = new File(inFilePath);
        InputStream in = new FileInputStream(file);
        File saveFile = new File(outFilePath);
        BufferedImage srcImage = ImageIO.read(in);

        String gif = inFilePath.substring(inFilePath.lastIndexOf(".") + 1, inFilePath.length());

/*        if ((gif.equals("gif") || gif.equals("GIF")) && smooth == true) // gif动态图片的处理
        {
            IamgesResize.getGifImage(inFilePath, outFilePath, width, hight, true);
        }
        else*/
        {
            // 高度按照原始图片里德等比缩放
            if (width>0 && hight<0)
            {
                double sx = (double) width / srcImage.getWidth();
                hight = (int) (srcImage.getHeight() * sx);
            }
            // 高度按照原始图片里德等比缩放
            if (hight>0 && width<0  )
            {
                double sx = (double) hight / srcImage.getHeight();
                width = (int) (srcImage.getWidth() * sx);
            }
            log.info("原理图片路径------>" + inFilePath);
            log.info("保存图片新路径------>" + saveFile);

            if (width > 0 || hight > 0)
            {
                // 原图的大小
                int sw = srcImage.getWidth();
                int sh = srcImage.getHeight();
                log.info("原图宽=" + sw);
                log.info("原图高=" + sh);
                // 如果原图像的大小小于要缩放的图像大小，直接将要缩放的图像复制过去
                /*if (sw > width && sh > hight)*/
                {
                    srcImage = rize(srcImage, width, hight);
                }
              /*  else
                {
                    log.info("原图片的大小小于要缩放的大小，不需要缩小");
                    String fileName = saveFile.getName();
                    String formatName = fileName.substring(fileName.lastIndexOf('.') + 1);
                    ImageIO.write(srcImage, formatName, saveFile);
                    return;
                }*/
            }
            // 缩放后的图像的宽和高
            int w = srcImage.getWidth();
            int h = srcImage.getHeight();
            log.info("缩小图片宽度= " + w);
            log.info("缩小图片高度= " + h);

            // 如果缩放后的图像和要求的图像宽度一样，就对缩放的图像的高度进行截取
            if (w == width)
            {
                // 计算 X轴坐标
                int x = 0;

                // 如果图片超过指定高度则截取一定的高度
/*                if (h >= maxHight && width != 600) // 图片为600 的不需要截取高度
                {
                    int y = h / 2 - hight / 2;
                    saveSubImage(srcImage, new Rectangle(x, y, width, maxHight), saveFile);
                }
                else*/
                {
                    int y = h / 2 - hight / 2;
                    saveSubImage(srcImage, new Rectangle(x, y, width, hight), saveFile);
                }

            }
            // 否则如果是缩放后的图像的高度和要求的图像高度一样，就对缩放后的图像的宽度进行截取
            else if (h == hight)
            {
                // 计算X轴坐标
                int x = w / 2 - width / 2;
                int y = 0;
                saveSubImage(srcImage, new Rectangle(x, y, width, hight), saveFile);
            }
            in.close();
        }
    }

    /** @param srcPath
     *            图片的绝对路径
     * @param width
     *            图片要缩放的宽度
     * @param height
     *            图片要缩放的高度
     * @param rizeType
     *            图片要缩放的类型（1：宽度固定，高度自动 2：按宽度和高度比例缩小）
     * @return */
    public static BufferedImage rize(BufferedImage srcBufImage, int width, int height)
    {
        BufferedImage bufTarget = null;
        int type = srcBufImage.getType();
        double sx = (double) width / srcBufImage.getWidth();
        double sy = (double) height / srcBufImage.getHeight();

        log.info("w=" + sx);
        log.info("h=" + sx);

        if (type == BufferedImage.TYPE_CUSTOM)
        {
            ColorModel cm = srcBufImage.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            bufTarget = new BufferedImage(cm, raster, alphaPremultiplied, null);
        }
        else
        {
            bufTarget = new BufferedImage(width, height, type);
        }

        Graphics2D g = bufTarget.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(srcBufImage, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return bufTarget;
    }

    /** 实现缩放后的截图
     * 
     * @param image
     *            缩放后的图像
     * @param subImageBounds
     *            要截取的子图的范围
     * @param subImageFile
     *            要保存的文件
     * @throws IOException */
    private static void saveSubImage(BufferedImage image, Rectangle subImageBounds, File subImageFile)
            throws IOException
    {
        if (subImageBounds.x < 0 || subImageBounds.y < 0 || subImageBounds.width - subImageBounds.x > image.getWidth()
                || subImageBounds.height - subImageBounds.y > image.getHeight())
        {
            log.info("Bad   subimage   bounds");
            return;
        }
        BufferedImage subImage = image.getSubimage(subImageBounds.x, subImageBounds.y, subImageBounds.width,
                subImageBounds.height);
        String fileName = subImageFile.getName();
        String formatName = fileName.substring(fileName.lastIndexOf('.') + 1);
        ImageIO.write(subImage, formatName, subImageFile);
    }

    /** 针对书签截屏的等比缩放(等比缩放，不失真)
     * 
     * @param src
     *            源图片文件完整路径
     * @param dist
     *            目标图片文件完整路径
     * @param width
     *            缩放的宽度
     * @param heightw
     *            缩放的高度 */
    public static void createThumbnail(String src, String dist, float width, float height)
    {
        try
        {
            File srcfile = new File(src);
            if (!srcfile.exists())
            {
                log.error("文件不存在");
                return;
            }
            BufferedImage image = ImageIO.read(srcfile);

            // 获得缩放的比例
            double ratio = 1.0;
            // 判断如果高、宽都不大于设定值，则不处理
            if (image.getHeight() > height || image.getWidth() > width)
            {
                if (image.getHeight() > image.getWidth())
                {
                    ratio = height / image.getHeight();
                }
                else
                {
                    ratio = width / image.getWidth();
                }
            }
            // 计算新的图面宽度和高度
            int newWidth = (int) (image.getWidth() * ratio);
            int newHeight = (int) (image.getHeight() * ratio);

            BufferedImage bfImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            bfImage.getGraphics().drawImage(image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0,
                    null);

            FileOutputStream os = new FileOutputStream(dist);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
            encoder.encode(bfImage);
            os.close();
            log.info("创建缩略图成功");
        }
        catch (Exception e)
        {
            log.error("创建缩略图发生异常" + e.getMessage());
        }
    }

    /** 实现图像的等比缩放和缩放后的截取，如果高度的值和宽度一样，则缩放按设置的值缩放 (只控制宽度的大小，高度的值设置不生效(只有高度的值和宽度的一样才生效)， 高度自动按比例缩放；如果缩放的图片小于你设置的值则保存原图大小)
     * 如果要缩放的宽度和高度相等则不按比例缩放；直接缩小图片
     * 
     * @param filepath
     *            要缩放图片文件的路径
     * @param saveFilePath
     *            缩放后保存图片输出的路径
     * @param imgWidth
     *            要缩放的宽度
     * @param imHeight
     *            要缩放的高度
     * @return
     * @throws Exception */
    public static boolean readPicfile(String filepath, String saveFilePath, int imgWidth, int imHeight)
            throws Exception
    {
        File file = new File(filepath);

        if (file.isDirectory())
        { // 如果path表示的是否是文件夹，是返回true
            log.info("文件夹");
            String[] filelist = file.list();
            log.info("总图片个数=" + filelist.length);
            int count = 0;
            for (int i = 0; i < filelist.length; i++)
            {
                File readfile = new File(filepath + filelist[i]);
                if (!readfile.isDirectory())
                {
                    log.info("absolutepath=" + readfile.getAbsolutePath());
                    log.info("imgName=" + readfile.getName());
                    String imgfuffix = readfile.getName().substring(readfile.getName().lastIndexOf(".") + 1,
                            readfile.getName().length()); // 获取文件的后缀
                    // 是图片类型的才执行缩放
                    if (isFromImgUrl(imgfuffix))
                    {
                        count++;
                        ImagesResize.zoomOutImage(filepath + readfile.getName(), saveFilePath + readfile.getName(),
                                imgWidth, imHeight, true);
                       /* ImagesResize.createThumbnail(filepath + readfile.getName(), saveFilePath + readfile.getName(),
                                imgWidth, imHeight);*/
                    }

                }
            }
        }
        return true;
    }

    /** gif图片缩放有动态效果
     * 
     * @param srcImg
     *            原始文件
     * @param destImg
     *            要保存的文件
     * @param width
     *            宽度
     * @param height
     *            高度
     * @param smooth
     * @throws Exception */
  /*  public static void getGifImage(String srcImg, String destImg, int width, int hight, boolean smooth)
            throws Exception
    {
        try
        {
            File file = new File(srcImg);
            File saveFile = new File(destImg);
            InputStream in = new FileInputStream(srcImg);
            BufferedImage srcImage = ImageIO.read(in);

            GifImage gifImage = GifDecoder.decode(file);// 创建一个GifImage对象.
            if (width > 0 || hight > 0)
            {
                // 原图的大小
                int sw = srcImage.getWidth();
                int sh = srcImage.getHeight();

                if (width == hight)
                {
                }
                else if (sw > width)
                {
                    double sx = (double) width / srcImage.getWidth();
                    hight = (int) (srcImage.getHeight() * sx);
                }
                else
                {
                    width = sw;
                    hight = sh;
                }
            }
            // 1.缩放重新更改大小.
            GifImage resizeIMG = GifTransformer.resize(gifImage, width, hight, true);
            // 2.剪切图片演示.
            // Rectangle rect = new Rectangle(0,0,200,200);
            // GifImage cropIMG = GifTransformer.crop(gifImage, rect);
            // 3.按比例缩放
            // GifImage resizeIMG = GifTransformer.scale(gifImage, 1.0, 1.0,true);//参数需要double型
            // 4.其他的方法.还有很多,比如水平翻转，垂直翻转 等等.都是GifTransformer类里面的.
            GifEncoder.encode(resizeIMG, saveFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            log.debug("保存失败（该图为修改过得gif图片），重新保存一次。");
            IamgesResize.zoomOutImage(srcImg, destImg, width, hight, false);
        }

    }*/

    /** 判断网址是不是图片类型。
     * 
     * @param fromUrl
     * @return */
    public static boolean isFromImgUrl(String imgfuffix)
    {
        boolean isImage = false;

        // 支持的图片后缀。
        String[] imgSuffixs = { "jpg", "JPG", "jpeg", "JPEG", "gif", "GIF", "png", "PNG", "bmp", "BMP" };
        for (int i = 0; i < imgSuffixs.length; i++)
        {
            if (imgfuffix.equals(imgSuffixs[i]))
            {
                isImage = true;
                break;
            }
        }
        return isImage;
    }
    
    public static void main(String[] args) {
    	String inFilePath ="D:/test/4-4.jpg";
    	String outFilePath ="D:/test/4-5.jpg";
    	ImagesResize aimg=new ImagesResize();
    	try {
		    // 处理单个的文件
			aimg.zoomOutImage(inFilePath,outFilePath,-1,100,false); //等比缩放
			aimg.zoomOutImage(inFilePath,outFilePath,200,100,false); //固定大小
		    // 处理整个文件夹
    		aimg.readPicfile("D:/test/","D:/test2/",200,100);
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
