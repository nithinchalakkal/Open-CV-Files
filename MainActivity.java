package smartvision.captureimage;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

	// Used to load the 'native-lib' library on application startup.
	static {
		System.loadLibrary("native-lib");
		System.loadLibrary("opencv_java3");
	}
	MyCameraView mOpenCvCameraView;
	Mat Rgba;
	Mat ProcessedMatObj;
	List<Camera.Size> mResolutionList;
	BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			if (status == LoaderCallbackInterface.SUCCESS) {
				mOpenCvCameraView.enableView();

			} else {
				super.onManagerConnected(status);
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mOpenCvCameraView = (MyCameraView) findViewById(R.id.mOpenCvCameraView);
		mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
		mOpenCvCameraView.setCameraIndex(1);
		mOpenCvCameraView.setCvCameraViewListener(this);

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (OpenCVLoader.initDebug()) {
			//Log.d(TAG, "open cv loaded successfully");
			mLoaderCallback.onManagerConnected(BaseLoaderCallback.SUCCESS);
		} else {
			OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, MainActivityTest.this, mLoaderCallback);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		mOpenCvCameraView.disableView();
	}
	public CascadeClassifier loadClassifier(int rawResId, String filename, File cascadeDir) {
		CascadeClassifier classifier = null;
		try {
			InputStream is = getResources().openRawResource(rawResId);
			File cascadeFile = new File(cascadeDir, filename);
			FileOutputStream os = new FileOutputStream(cascadeFile);
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = is.read(buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			is.close();
			os.close();
			classifier = new CascadeClassifier(cascadeFile.getAbsolutePath());
			if (classifier.empty()) {
				classifier = null;
			} else {
			}

		} catch (IOException e) {
			e.printStackTrace();

		}
		return classifier;
	}
	@Override
	public void onCameraViewStarted(int width, int height) {

		Rgba = new Mat(new Size(width, height), CvType.CV_8UC4);
		ProcessedMatObj = new Mat(new Size(width, height), CvType.CV_8UC4);
		mResolutionList = mOpenCvCameraView.getResolutionList();
		@SuppressWarnings("deprecation") Camera.Size resolution = mResolutionList.get(0);
		resolution.width = 420;
		resolution.height = 240;
		mOpenCvCameraView.setResolution(resolution);

	}
	@Override
	public void onCameraViewStopped() {
		Rgba.release();
		ProcessedMatObj.release();

	}
	@Override
	public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
		Rgba = inputFrame.rgba();
		ProcessedMatObj = Rgba;
		ProcessedMatObj = ConvertionMethods.Flipping(ProcessedMatObj);
		return ProcessedMatObj;
	}





}

