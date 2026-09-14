import ij.ImagePlus;

public class projectionObject {
    
    //the projecting image id
    private int projectingImageID;

    //the projected image id
    private int projectedImageID;

    //the projecting path
    private String projectingPath;
    
    //the projected path
    private String projectedPath;

    //the projecting name
    private String projectingName;

    //the projected name
    private String projectedName;

    //the projecting image
    private ImagePlus projectingImage;
    
    //the projected image
    private ImagePlus projectedImage;
    
    //the projection type
    private String projectionType;

    //the channels int
    private int projectingChannelNumber;

    //the slices int
    private int projectingSliceNumber;

    //the frames int
    private int projectingFrameNumber;

    //the projecting channels
    private int[] projectingChannels;

    //the projecting slices
    private int[] projectingSlices;

    //the projecting frames
    private int[] projectingFrames;

    //#region ############################### CONSTRACTOR ###############################

    public projectionObject(ImagePlus projectingImage) {
        
        //set the values
        this.projectingImage = projectingImage;

        //set the rest to defaults
        this.projectingPath = null;
        this.projectedPath = null;
        this.projectingImageID = 0;
        this.projectedImageID = 0;
        this.projectedImage = null;
        this.projectionType = null;
        this.projectingChannelNumber = 0;
        this.projectingSliceNumber = 0;
        this.projectingFrameNumber = 0;
        this.projectingChannels = null;
        this.projectingSlices = null;
        this.projectingFrames = null;

    }

    //#endregion ############################ CONSTRUCTOR ###############################

    //#region ############################### GETTERS & SETTERS #########################

    public int getProjectingImageID() {
        return projectingImageID;
    }

    public void setProjectingImageID(int projectingImageID) {
        this.projectingImageID = projectingImageID;
    }

    public int getProjectedImageID() {
        return projectedImageID;
    }

    public void setProjectedImageID(int projectedImageID) {
        this.projectedImageID = projectedImageID;
    }

    public String getProjectingPath() {
        return projectingPath;
    }

    public void setProjectingPath(String projectingPath) {
        this.projectingPath = projectingPath;
    }

    public String getProjectedPath() {
        return projectedPath;
    }

    public void setProjectedPath(String projectedPath) {
        this.projectedPath = projectedPath;
    }

    public ImagePlus getProjectingImage() {
        return projectingImage;
    }

    public String getProjectingName() {
        return projectingName;
    }

    public void setProjectingName(String projectingName) {
        this.projectingName = projectingName;
    }

    public String getProjectedName() {
        return projectedName;
    }

    public void setProjectedName(String projectedName) {
        this.projectedName = projectedName;
    }

    public void setProjectingImage(ImagePlus projectingImage) {
        this.projectingImage = projectingImage;
    }

    public ImagePlus getProjectedImage() {
        return projectedImage;
    }

    public void setProjectedImage(ImagePlus projectedImage) {
        this.projectedImage = projectedImage;
    }

    public String getProjectionType() {
        return projectionType;
    }

    public void setProjectionType(String projectionType) {
        this.projectionType = projectionType;
    }

    public int getChannels() {
        return projectingChannelNumber;
    }

    public void setChannels(int channels) {
        this.projectingChannelNumber = channels;
    }

    public int getSlices() {
        return projectingSliceNumber;
    }

    public void setSlices(int slices) {
        this.projectingSliceNumber = slices;
    }

    public int getFrames() {
        return projectingFrameNumber;
    }

    public void setFrames(int frames) {
        this.projectingFrameNumber = frames;
    }

    public int[] getProjectingChannels() {
        return projectingChannels;
    }

    public void setProjectingChannels(int[] projectingChannels) {
        this.projectingChannels = projectingChannels;
    }

    public int[] getProjectingSlices() {
        return projectingSlices;
    }

    public void setProjectingSlices(int[] projectingSlices) {
        this.projectingSlices = projectingSlices;
    }

    public int[] getProjectingFrames() {
        return projectingFrames;
    }

    public void setProjectingFrames(int[] projectingFrames) {
        this.projectingFrames = projectingFrames;
    }
    
    //#endregion ############################ GETTERS & SETTERS ######################### 

}
