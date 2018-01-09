package smartvision.captureimage;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;

import org.opencv.android.JavaCameraView;

import java.util.List;

public class MyCameraView extends JavaCameraView {

    private static final String TAG = MyCameraView.class.getSimpleName();
    private String mPictureFileName;
    //  private Camera.Parameters mParameters = mCamera.getParameters();
    public MyCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public List<String> getEffectList() {
        return mCamera.getParameters().getSupportedColorEffects();
    }

    public boolean isEffectSupported() {
        return (mCamera.getParameters().getColorEffect() != null);
    }

    public String getEffect() {
        return mCamera.getParameters().getColorEffect();
    }

    public void setEffect(String effect) {
        Camera.Parameters params = mCamera.getParameters();
        params.setColorEffect(effect);
        mCamera.setParameters(params);
    }

    public List<Camera.Size> getResolutionList() {
        return mCamera.getParameters().getSupportedPreviewSizes();
    }

    public void setResolution(Camera.Size resolution) {
        disconnectCamera();
        mMaxHeight = resolution.height;
        mMaxWidth = resolution.width;
        connectCamera(getWidth(), getHeight());
    }

    public Camera.Size getResolution() {
        return mCamera.getParameters().getPreviewSize();
    }

    public boolean isAutoWhiteBalanceLockSupported() {
        //return mParameters.isAutoWhiteBalanceLockSupported();
        return mCamera.getParameters().isAutoWhiteBalanceLockSupported();
    }

    public boolean getAutoWhiteBalanceLock () {
        //return mParameters.getAutoWhiteBalanceLock();
        return mCamera.getParameters().getAutoWhiteBalanceLock();
    }

    public void setAutoWhiteBalanceLock (boolean toggle) {
        Camera.Parameters params = mCamera.getParameters();
        params.setAutoWhiteBalanceLock(toggle);
        mCamera.setParameters(params);
    }

    public String getFocusMode () {
        return mCamera.getParameters().getFocusMode();
    }

    public void setFocusModeFixed () {
        Camera.Parameters params = mCamera.getParameters();
        params.setFocusMode(params.FOCUS_MODE_FIXED);
        mCamera.setParameters(params);
    }

    public void setFocusModeCon() {
        Camera.Parameters params = mCamera.getParameters();
        params.setFocusMode(params.FOCUS_MODE_CONTINUOUS_VIDEO);
        mCamera.setParameters(params);
    }

    public void startAutoFocus(Camera.AutoFocusCallback cb) {
        mCamera.autoFocus(cb);
    }

    public int getMaxNumFocusAreas () {
        return mCamera.getParameters().getMaxNumFocusAreas();
    }
    public boolean isAutoExposureLockSupported () {
        return mCamera.getParameters().isAutoExposureLockSupported();
    }

    public boolean getAutoExposureLock () {
        return mCamera.getParameters().getAutoExposureLock();
    }

    public void setAutoExposureLock (boolean toggle) {
        Camera.Parameters params = mCamera.getParameters();
        params.setAutoExposureLock(toggle);
        mCamera.setParameters(params);
    }

    public void capture(Camera.ShutterCallback shutter, Camera.PictureCallback raw, Camera.PictureCallback postview, Camera.PictureCallback jpeg){
        mCamera.takePicture(shutter, raw, postview, jpeg);
    }

    public void capture(Camera.ShutterCallback shutter, Camera.PictureCallback raw, Camera.PictureCallback jpeg){
        mCamera.takePicture(shutter, raw, jpeg);
    }
}
