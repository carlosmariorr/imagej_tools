import java.util.Arrays;

import ij.IJ;
import ij.ImagePlus;
import ij.io.FileInfo;
import ij.process.ImageProcessor;

public class projectionManager {
    
    /* This is the class that handles projections. 
     * Just call the method and the projections will be returned */


    //#region ######################## VARIABLES ########################



    //#endregion  #################### VARIABLES ########################

    //#region ######################## METHODS ##########################

    /*
    public static ImagePlus () {

        //get the image plus by opening "/extra_data/c073/pansyp1/gfpcosa1/gonad1/decon/c073_2023_9_14_strain_gfpcosa1_stain_dapi_gfp_pansyp1_chhtp3_slide1_gonad1_diplotene01_2_sbs1_cut.tif"
        ImagePlus imagePlus = IJ.openImage("/extra_data/testing_nan.tif");

        //get the image channels
        int imageChannels = imagePlus.getNChannels();

        //get a list of the channels to project
        int[] projectingChannels = new int[imageChannels];

        //loop through the image channels and populate the array
        for (int a = 0; a < imageChannels; a++) {

            //set the channel
            projectingChannels[a] = a + 1;

        }

        //get the image slices
        int imageSlices = imagePlus.getNSlices();

        //get the image time points
        int imageTimePoints = imagePlus.getNFrames();

        //set the array
        int[] projectingTimePoints = new int[imageTimePoints];

        //loop through the time points and populate the array
        for (int a = 0; a < imageTimePoints; a++) {

            //set the time point
            projectingTimePoints[a] = a + 1;

        }

        //set the type
        String typeOfProjection = "max_int";

        //make the sum projection
        ImagePlus sumProjected = makeProjection(imagePlus, imageSlices, projectingChannels, projectingTimePoints, typeOfProjection);

    }
    */

    //method to make the sum projection
    public static ImagePlus makeProjection(ImagePlus projectingImage, int[] projectingSlices, int[] projectingChannels, int[] projectingTimePoints, String type) {

        //get the image width
        int imageWidth = projectingImage.getWidth();

        //get the image height
        int imageHeight = projectingImage.getHeight();

        //get the return image channels
        int returnImageChannels_length = projectingChannels.length;

        //get the returning time points
        int returnImageTimePoints_length = projectingTimePoints.length;
        
        //get the projectingImage slices length
        int projectingSlices_length = projectingSlices.length;

        //set the image title
        String imageTitle = "" + type + "_projection";

        //create a new image with the same number of time points, channels, and just one slice
        ImagePlus returningImage = IJ.createImage(imageTitle, "32-bit", imageWidth, imageHeight, returnImageChannels_length, 1, returnImageTimePoints_length);

        //set to grayscale
        returningImage.setDisplayMode(IJ.GRAYSCALE);

        //get the file info
        FileInfo projectingImageInfo = projectingImage.getFileInfo();

        //get the pixel width
        double pixelWidth = projectingImageInfo.pixelWidth;

        //round it to 4 decimal points
        pixelWidth = Math.round(pixelWidth * 10000.0) / 10000.0;

        //get the pixel height
        double pixelHeight = projectingImageInfo.pixelHeight;

        //round it to 4 decimal points
        pixelHeight = Math.round(pixelHeight * 10000.0) / 10000.0;

        //get the pixel depth
        double pixelDepth = projectingImageInfo.pixelDepth;

        //round it to 4 decimal points
        pixelDepth = Math.round(pixelDepth * 10000.0) / 10000.0;

        //get the unit
        String unit = projectingImageInfo.unit;

        //get the FileInfo of the returning image
        FileInfo returningImageInfo = returningImage.getFileInfo();

        //set the pixel width
        returningImageInfo.pixelWidth = pixelWidth;

        //get the pixels per micron
        double pixelsPerMicron = 1 / pixelWidth;

        //set the pixel height
        returningImageInfo.pixelHeight = pixelHeight;

        //set the pixel depth
        returningImageInfo.pixelDepth = pixelDepth;

        //set the unit
        returningImageInfo.unit = unit;

        //set the image info
        returningImage.setFileInfo(returningImageInfo);
        
        //set the scale
        IJ.run(returningImage, "Set Scale...", "distance="+pixelsPerMicron+" known=1 unit=" + unit);

        //make the actual projection
        //loop through the time points
        //get the current time point
        int current_time_point = 0;
        int current_channel = 0;

        //project the image
        for (int a = 0; a < returnImageTimePoints_length; a++) {
            
            //set the projecting image to the current time point
            projectingImage.setT(projectingTimePoints[a]);

            //set the returning image to the current time point
            returningImage.setT(current_time_point + 1);
            
            //loop through the channels
            for (int b = 0; b < returnImageChannels_length; b++) {

                //set the projecting channel
                projectingImage.setC(projectingChannels[b]);

                //set the returning channel
                returningImage.setC(current_channel + 1);

                //loop through the width
                for (int c = 0; c < imageWidth; c++) {

                    //loop through the height
                    for (int d = 0; d < imageHeight; d++) {

                        //start a new float[] with the number of slices and fill it with NaNs
                        float[] pixelValues = new float[projectingSlices_length];

                        //fill the array with NaNs
                        Arrays.fill(pixelValues, Float.NaN);

                        //the setting value
                        float settingValue = 0.0f;

                        //set the count
                        int count_pixel = 0;

                        //loop through the slices
                        for (int e = 0; e < projectingSlices_length; e++) {

                            //set the projecting slice
                            projectingImage.setZ(projectingSlices[e]);

                            //get the image processor
                            ImageProcessor projectingImageProcessor = projectingImage.getProcessor();

                            //get the pixel value
                            float pixelValue = projectingImageProcessor.getPixelValue(c, d);
                            
                            //set it on the array
                            pixelValues[e] = pixelValue;

                            //if the value is not nan
                            if (!Float.isNaN(pixelValue)) {

                                //add one to the count
                                count_pixel++;

                            }

                        }

                        //if the count is more than 0
                        if (count_pixel > 0) {
                            
                            //if the type is sum
                            if (type.equals("sum")) {

                                //run the sum method
                                settingValue = getPixelSum(pixelValues);               

                            }

                            //if the type is average
                            if (type.equals("avg")) {

                                //set the setting value to the average
                                settingValue = getPixelSum(pixelValues) / count_pixel;

                            }

                            //if the type is max_intensity
                            if (type.equals("max_int")) {

                                //set the setting value to the max
                                settingValue = getMaxPixel(pixelValues);

                            }

                            //if the type is min int
                            if (type.equals("min_int")) {

                                //set the setting value to the min
                                settingValue = getMinPixel(pixelValues);

                            }

                            //if the type is median
                            if (type.equals("median")) {

                                //get the median 
                                settingValue = getMedianValue(pixelValues, count_pixel);

                            }
                            
                            //if the type is standard deviation
                            if (type.equals("std_dev")) {

                                //get the median 
                                settingValue = getStandardDeviation(pixelValues, count_pixel);

                            }

                        }

                        //if the count is 0
                        else {

                            //set the setting value to NaN
                            settingValue = Float.NaN;

                        }

                        //get the returning image processor
                        ImageProcessor returningImageProcessor = returningImage.getProcessor();

                        //set the pixel value
                        returningImageProcessor.putPixelValue(c, d, settingValue);

                    }

                }

                //add one to the current channel
                current_channel++;

            }

            //add one to the current time point
            current_time_point++;

        }

        //return it
        return returningImage;

    }

    //METHOD TO SUM THE PIXELS
    public static float getPixelSum(float[] pixelValues) {

        //set the sum
        float sum = 0.0f;

        //loop through the array
        for (int a = 0; a < pixelValues.length; a++) {

            //get the current value
            float currentValue = pixelValues[a];

            //if the current value is not NaN
            if (!Float.isNaN(currentValue)) {

                //add it to the sum
                sum += currentValue;

            }

        }

        //return the sum
        return sum;

    }

    //Method to get the max pixels
    public static float getMaxPixel(float[] pixelValues) {

        //set the max
        float max = 0.0f;

        //loop through the array
        for (int a = 0; a < pixelValues.length; a++) {

            //get the current value
            float currentValue = pixelValues[a];

            //if the current value is not NaN
            if (!Float.isNaN(currentValue)) {

                //if the current value is more than the max
                if (currentValue > max) {

                    //set the max to the current value
                    max = currentValue;

                }

            }

        }

        //return the max
        return max;

    }

    //Method to get the min pixels
    public static float getMinPixel(float[] pixelValues) {

        //set the min
        float min = 0.0f;

        //loop through the array
        for (int a = 0; a < pixelValues.length; a++) {

            //get the current value
            float currentValue = pixelValues[a];

            //if the current value is not NaN
            if (!Float.isNaN(currentValue)) {

                //if the current value is more than the max
                if (currentValue < min) {

                    //set the max to the current value
                    min = currentValue;

                }

            }

        }

        //return the max
        return min;

    }

    //Method to get the median value
    public static float getMedianValue(float[] pixelValues, int pixel_count) {

        //set the median
        float median = 0.0f;

        //get the new arra with the length of the pixel count
        float[] nonNaNPixelValues = new float[pixel_count];
        
        //start an adding count
        int adding_count = 0;

        //loop through the pixel values and is it is not NaN, add it to the nonNaNPixelValues
        for (int a = 0; a < pixelValues.length; a++) {

            //get the current value
            float currentValue = pixelValues[a];

            //if the current value is not NaN
            if (!Float.isNaN(currentValue)) {

                //add it to the nonNaNPixelValues
                nonNaNPixelValues[adding_count] = currentValue;

                //add one to the adding count
                adding_count++;

            }

        }

        //sort the array
        Arrays.sort(nonNaNPixelValues);

        //if the pixel count is even
        if (pixel_count % 2 == 0) {

            //get the two middle values
            float middleValue1 = nonNaNPixelValues[(pixel_count / 2) - 1];
            float middleValue2 = nonNaNPixelValues[(pixel_count / 2)];

            //get the average
            median = (middleValue1 + middleValue2) / 2;

        }

        //if the pixel count is odd
        else {

            //get the middle value
            median = nonNaNPixelValues[(pixel_count / 2)];

        }


        //return the median
        return median;

    }

    //Method to get the standard deviation
    public static float getStandardDeviation(float[] pixelValues, int pixel_count) {

        //set the std 
        float std = 0.0f;

        //get the new arra with the length of the pixel count
        float[] nonNaNPixelValues = new float[pixel_count];

        //start an adding count
        int adding_count = 0;

        //loop through the pixel values and is it is not NaN, add it to the nonNaNPixelValues
        for (int a = 0; a < pixelValues.length; a++) {

            //get the current value
            float currentValue = pixelValues[a];

            //if the current value is not NaN
            if (!Float.isNaN(currentValue)) {

                //add it to the nonNaNPixelValues
                nonNaNPixelValues[adding_count] = currentValue;

                //add one to the adding count
                adding_count++;

            }

        }

        //get the average
        float average = getPixelSum(nonNaNPixelValues) / pixel_count;

        //get the sum of the squares
        float sumOfSquares = 0.0f;

        //loop through the nonNaNPixelValues
        for (int a = 0; a < nonNaNPixelValues.length; a++) {

            //get the current value
            float currentValue = nonNaNPixelValues[a];

            //get the difference
            float difference = currentValue - average;

            //get the square
            float square = difference * difference;

            //add it to the sum of squares
            sumOfSquares += square;

        }

        //get the variance
        float variance = sumOfSquares / pixel_count;

        //get the std
        std = (float) Math.sqrt(variance);

        //return the std
        return std;

    }

    ////#endregion ################### METHODS ##########################



}
