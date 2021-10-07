package com.payoneer.checkout.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import com.payoneer.checkout.core.WorkerSubscriber;
import com.payoneer.checkout.core.WorkerTask;
import com.payoneer.checkout.core.Workers;
import com.payoneer.checkout.network.ImageConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * This class will handle the loading of images from the network instead of using
 * a library
 */
public final class ImageHelper {
    private final ImageConnection imageConnection;

    private ImageHelper() {
        this.imageConnection = new ImageConnection();
    }

    /**
     * Get the instance of this ImageHelper
     *
     * @return the instance of this ImageHelper
     */
    public static ImageHelper getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * Load the image from the assets and store it into the view once loaded.
     *
     * @param view ImageView in which to place the drawable
     * @param fileName  is the name of the image in the assets folder
     */
    public void loadImageFromFile(ImageView view, String fileName) {
        if (hasImage(view)) {
            return;
        }
        WorkerTask<Drawable> task = WorkerTask.fromCallable(() -> loadFromFile(view.getContext(), fileName));
        task.subscribe(new WorkerSubscriber<Drawable>() {
            @Override
            public void onSuccess(Drawable drawable) {
                view.setImageDrawable(drawable);
            }

            @Override
            public void onError(Throwable cause) {
                Log.w("sdk_ImageHelper", cause);
                // we ignore image loading failures
            }
        });
        Workers.getInstance().forImageTasks().execute(task);
    }

    /**
     * Load the image from the URL and store it into the view once loaded.
     *
     * @param view ImageView in which to place the drawable
     * @param url  is the url of the image
     */
    public void loadImageFromNetwork(final ImageView view, final URL url) {
        if (hasImage(view)) {
            return;
        }
        WorkerTask<Bitmap> task = WorkerTask.fromCallable(() -> imageConnection.loadBitmap(url));
        task.subscribe(new WorkerSubscriber<Bitmap>() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                callbackLoadBitmapSuccess(view, bitmap);
            }

            @Override
            public void onError(Throwable cause) {
                Log.w("sdk_ImageHelper", cause);
                // we ignore image loading failures
            }
        });
        Workers.getInstance().forImageTasks().execute(task);
    }

    private boolean hasImage(ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable) drawable).getBitmap() != null;
        }
        return hasImage;
    }

    private void callbackLoadBitmapSuccess(ImageView view, Bitmap bitmap) {
        try {
            view.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.w("sdk_ImageHelper", e);
            // we ignore image loading failures
        }
    }

    private Drawable loadFromFile(Context context, String fileName) {
        Drawable drawable = null;
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            drawable = Drawable.createFromStream(inputStream, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return drawable;
    }

    private static class InstanceHolder {
        static final ImageHelper INSTANCE = new ImageHelper();
    }
}
