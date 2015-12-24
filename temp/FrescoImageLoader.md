# Fresco utility class


Here is the class! :+1:

```java
public class FrescoUtils {
    public static final String TAG = "FrescoUtils";

    public interface LoadingSingleImageListener{
        void onSuccess(File imageFile);
        void onFailed();
    }

    public static boolean isImageDownloaded(Uri loadUri) {
        if (loadUri == null) {
            return false;
        }
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(ImageRequest.fromUri(loadUri));
        return ImagePipelineFactory.getInstance().getMainDiskStorageCache().hasKey(cacheKey) || ImagePipelineFactory.getInstance().getSmallImageDiskStorageCache().hasKey(cacheKey);
    }

    /**
     * Get the cached file from fresco
     * @see https://github.com/facebook/fresco/issues/80
     * @see http://stackoverflow.com/questions/29772949/android-how-to-get-image-file-from-fresco-disk-cache
     * @param loadUri Uri
     * @return File the file of the
     */
    public static File getCachedImageOnDisk(Uri loadUri) {
        File localFile = null;
        if (loadUri != null) {
            CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(ImageRequest.fromUri(loadUri));
            if (ImagePipelineFactory.getInstance().getMainDiskStorageCache().hasKey(cacheKey)) {
                BinaryResource resource = ImagePipelineFactory.getInstance().getMainDiskStorageCache().getResource(cacheKey);
                localFile = ((FileBinaryResource) resource).getFile();
            } else if (ImagePipelineFactory.getInstance().getSmallImageDiskStorageCache().hasKey(cacheKey)) {
                BinaryResource resource = ImagePipelineFactory.getInstance().getSmallImageDiskStorageCache().getResource(cacheKey);
                localFile = ((FileBinaryResource) resource).getFile();
            }
        }
        return localFile;
    }


    /**
     * @see http://frescolib.org/docs/listening-download-events.html#_
     * @param simpleDraweeView
     * @param uri
     * @param mListener
     */
    public static void loadImage(SimpleDraweeView simpleDraweeView, final Uri uri, final LoadingSingleImageListener mListener){
        ControllerListener<ImageInfo> listener = new BaseControllerListener<ImageInfo>(){
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                //TODO: try to download the files here
                File cachedFile = getCachedImageOnDisk(uri);
                if (cachedFile!=null && cachedFile.exists()){
                    //Log.i(TAG, "loadImage() cached file of image exists");
                }else{
                    if (cachedFile!=null && cachedFile.exists()){
                        //Log.i(TAG, "loadImage() cached file of image does not exist");
                    }
                }
                mListener.onSuccess(cachedFile);

            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                super.onFailure(id, throwable);
                //Log.i(TAG, "loadImage() failure of loading image");
                mListener.onFailed();
            }
        };
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setTapToRetryEnabled(true)
                .setOldController(simpleDraweeView.getController())
                .setControllerListener(listener)
                .build();
        simpleDraweeView.setController(controller);
    }

    public static void saveFile(String fileDir, String fileName){
        File dir = new File(fileDir);

    }

    /**
     * This method is reponsible for showing the company logo in a SimpleDraweeView.
     * If there is internet connection it will load the image and create a copy of the image for local cache.
     * If there is no internet connection this will check the local cache if it can retreive the image from there, if it is present it will then be displayed.
     *
     * @param mCompanyLogo
     * @param String imageUrl sample: http://google.com/image/sample.png
     */
    public static void showCompanyLogo(final SimpleDraweeView mCompanyLogo, String imageUrl){
        if (imageUrl != null) {
            /*
            Object obj = mCompanyLogo.getTag(R.string.visibility);
            if (obj==null)
                mCompanyLogo.setTag(R.string.visibility,0);
            if (obj!=null && (int)obj==1)
                return;
            */

            //mCompanyLogo.setImageURI(Uri.parse(imageUrl));
            FrescoUtils.loadImage(mCompanyLogo, Uri.parse(imageUrl), new FrescoUtils.LoadingSingleImageListener() {

                @Override
                public void onSuccess(File cachedFiel) {
                    //Log.i(TAG, "showCompanyLogo() onSuccess() should download image");
                    if (cachedFiel != null && cachedFiel.exists()) {
          
                        File outputFile = new File("/data/data/sample.png");
                        if (outputFile.exists())
                            outputFile.delete();
                        if (FileUtils.copyFile(cachedFiel, outputFile)) {
                            //Toast.makeText(getActivity(), "file copied2!", Toast.LENGTH_SHORT).show();
                            mCompanyLogo.setTag(R.string.visibility, 1);
                        } else {
                            //Toast.makeText(getActivity(), "file copy fail2", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //Toast.makeText(getActivity(), "file :(", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailed() {
                    //Toast.makeText(getActivity(), "company logo from custom cache", Toast.LENGTH_SHORT).show();
                    if (MyApplication.getCompanyLogoFile().exists()){
                        mCompanyLogo.setTag(R.string.visibility, 1);
                    }
                    mCompanyLogo.setImageURI(
                            Uri.fromFile(imageUrl)
                    );

                }
            });
        }
    }

}


```


Fresco: https://github.com/facebook/fresco

### Usage

```java
//add on your app's build.gradle
compile 'com.facebook.fresco:fresco:0.7.0+'

//initialize in your APPLICATION class first
Fresco.initialize(this.getApplicationContext());



//this will load the 2nd parameter image to the first parameter imageview
FrescoUtils.showCompanyLogo(simpleDraweeView,"http://sampleweb.com/images/trap.png");

```
