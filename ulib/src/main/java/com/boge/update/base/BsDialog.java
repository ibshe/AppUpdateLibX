package com.boge.update.base;
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.drawable.Drawable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.boge.update.R;
import com.boge.update.utils.AnimatorHelper;
import com.boge.update.widget.DialogViewHolder;
import com.boge.update.widget.OutsideClickDialog;

/**
 * @Author ibshen@aliyun.com
 */
public abstract class BsDialog {
    private OutsideClickDialog mDialog;
    private Window mDialogWindow;
    private DialogViewHolder dilaogVh;
    private View mRootView;

    private boolean cancelable = false;
    private boolean cancelableOnTouchOutside = false;

    private boolean isCustomAnima = false;

    private int mInAnimaType;
    private int mOutAnimaType;

    public BsDialog(final Context context, int layoutId) {
        dilaogVh = DialogViewHolder.get(context, layoutId);
        mRootView = dilaogVh.getConvertView();
        mDialog = new OutsideClickDialog(context, R.style.dialog) {
            @Override
            protected void onTouchOutside() {
                startOutAinma(cancelableOnTouchOutside);
            }
        };

        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                if (isCustomAnima) {
                    AnimatorHelper.getAnimator(mRootView, mInAnimaType).start();
                }
            }
        });

        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    startOutAinma(cancelable);
                    return true;
                }
                return false;
            }
        });
        mDialog.setContentView(mRootView);
        mDialogWindow = mDialog.getWindow();
        onBindViewHolder(dilaogVh);
    }

    public abstract void onBindViewHolder(DialogViewHolder holder);

    public BsDialog setBackground(Drawable drawable) {
        if (mDialog != null && mRootView!=null) {
            mRootView.setBackground(drawable);
        }
        return this;
    }

    /**
     * ??????dialog
     */
    public BsDialog showDialog() {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
        return this;
    }

    /**
     * @param style ????????????Dialog???????????????????????????  ??????????????? ?????????????????????
     * @return
     */
    public BsDialog showDialog(@StyleRes int style) {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialogWindow.setWindowAnimations(style);
            mDialog.show();
        }
        return this;
    }

    /**
     * @param isAnimation ?????????true ????????????????????????????????????
     * @return
     */
    public BsDialog showDialog(boolean isAnimation) {
        if (mDialog != null && !mDialog.isShowing()) {
            if (isAnimation) {
                mDialogWindow.setWindowAnimations(R.style.dialog_scale_animstyle);
            }
            mDialog.show();
        }
        return this;
    }

    /**
     * @param light ????????????????????? ??????0.0~1.0    1.0????????????  0.0????????????
     * @return
     */
    public BsDialog backgroundLight(double light) {
        if (mDialogWindow != null) {
            if (light < 0.0 || light > 1.0) {
                return this;
            }
            WindowManager.LayoutParams lp = mDialogWindow.getAttributes();
            lp.dimAmount = (float) light;
            mDialogWindow.setAttributes(lp);
        }
        return this;
    }

    /**
     * ??????
     *
     * @param view ???????????????????????????
     * @return
     */
    public BsDialog setViewTop(View view) {
        if (mDialogWindow != null) {
            mDialogWindow.setGravity(Gravity.TOP | Gravity.LEFT);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            WindowManager.LayoutParams params = mDialogWindow.getAttributes();
            params.x = location[0] + (view.getWidth() / 2) - (dilaogVh.getWidth() / 2);
            params.y = location[1] - dilaogVh.getHeight() - view.getHeight();
            mDialogWindow.setAttributes(params);
        }
        return this;
    }

    /**
     * ??????
     *
     * @param view ???????????????????????????
     * @return
     */
    public BsDialog setViewBottom(View view) {
        if (mDialogWindow != null) {
            mDialogWindow.setGravity(Gravity.TOP | Gravity.LEFT);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            WindowManager.LayoutParams params = mDialogWindow.getAttributes();
            params.x = location[0] + (view.getWidth() / 2) - (dilaogVh.getWidth() / 2);
            params.y = location[1] + 5;
            mDialogWindow.setAttributes(params);
        }
        return this;
    }

    /**
     * ??????
     *
     * @param view ???????????????????????????
     * @return
     */
    public BsDialog setViewLeft(View view) {
        if (mDialogWindow != null) {
            mDialogWindow.setGravity(Gravity.TOP | Gravity.LEFT);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            WindowManager.LayoutParams params = mDialogWindow.getAttributes();
            params.x = location[0] - dilaogVh.getWidth();
            params.y = location[1] - (dilaogVh.getHeight() / 2) - (view.getHeight() / 2);
            mDialogWindow.setAttributes(params);
        }
        return this;
    }

    /**
     * ??????
     *
     * @param view ???????????????????????????
     * @return
     */
    public BsDialog setViewRigh(View view) {
        if (mDialogWindow != null) {
            mDialogWindow.setGravity(Gravity.TOP | Gravity.RIGHT);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            WindowManager.LayoutParams params = mDialogWindow.getAttributes();
            params.x = dilaogVh.getScreenWidth() - location[0] - dilaogVh.getWidth() - view.getWidth();
            params.y = location[1] - (dilaogVh.getHeight() / 2) - (view.getHeight() / 2);
            mDialogWindow.setAttributes(params);
        }
        return this;
    }

    /**
     * ?????????
     *
     * @param view ???????????????????????????
     * @return
     */
    public BsDialog setViewLeftBottom(View view) {
        if (mDialogWindow != null) {
            mDialogWindow.setGravity(Gravity.TOP | Gravity.LEFT);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            WindowManager.LayoutParams params = mDialogWindow.getAttributes();
            params.x = location[0];
            params.y = location[1] + 5;
            mDialogWindow.setAttributes(params);
        }
        return this;
    }

    /**
     * ?????????
     *
     * @param view ???????????????????????????
     * @return
     */
    public BsDialog setViewRighBottom(View view) {
        if (mDialogWindow != null) {
            mDialogWindow.setGravity(Gravity.TOP | Gravity.RIGHT);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            WindowManager.LayoutParams params = mDialogWindow.getAttributes();
            params.x = dilaogVh.getScreenWidth() - location[0] - view.getWidth();
            params.y = location[1] + 5;
//            LogUtils.d("setViewRighBottom =" + params.x + " " + dilaogVh.getScreenWidth() + " " + location[0] + " " + view.getWidth());
            mDialogWindow.setAttributes(params);
        }
        return this;
    }

    /**
     * ?????????
     *
     * @param view ???????????????????????????
     * @return
     */
    public BsDialog setViewLeftTop(View view) {
        if (mDialogWindow != null) {
            mDialogWindow.setGravity(Gravity.TOP | Gravity.LEFT);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            WindowManager.LayoutParams params = mDialogWindow.getAttributes();
            params.x = location[0];
            params.y = location[1] - dilaogVh.getHeight() - view.getHeight() - 5;
            mDialogWindow.setAttributes(params);
        }
        return this;
    }

    /**
     * ?????????
     *
     * @param view ???????????????????????????
     * @return
     */
    public BsDialog setViewRighTop(View view) {
        if (mDialogWindow != null) {
            mDialogWindow.setGravity(Gravity.TOP | Gravity.RIGHT);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            WindowManager.LayoutParams params = mDialogWindow.getAttributes();
            params.x = dilaogVh.getScreenWidth() - location[0] - view.getWidth();
            params.y = location[1] - dilaogVh.getHeight() - view.getHeight() - 5;
            mDialogWindow.setAttributes(params);
        }
        return this;
    }


    /**
     * ?????????????????????
     */
    public BsDialog setCustomAnimations(final int inAnimType, int outAnimType) {
        isCustomAnima = true;
        this.mInAnimaType = inAnimType;
        this.mOutAnimaType = outAnimType;
        return this;
    }


    /**
     * ???????????????????????????
     */
    @SuppressLint("NewApi")
    public BsDialog fromBottomToMiddle() {
        if (mDialogWindow != null) {
            mDialogWindow.setWindowAnimations(R.style.window_bottom_in_bottom_out);
        }
        return this;
    }

    /**
     * ???????????????
     */
    public BsDialog fromBottom() {
        if (mDialogWindow != null) {
            fromBottomToMiddle();
            mDialogWindow.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        }
        return this;
    }

    /**
     * ????????????????????????????????????????????????
     */
    public BsDialog fromLeftToMiddle() {
        if (mDialogWindow != null) {
            mDialogWindow.setWindowAnimations(R.style.window_left_in_left_out);
            mDialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            mDialogWindow.setGravity(Gravity.CENTER | Gravity.LEFT);
        }
        return this;
    }

    /**
     * ????????????????????????????????????????????????
     *
     * @return
     */
    public BsDialog fromRightToMiddle() {
        if (mDialogWindow != null) {
            mDialogWindow.setWindowAnimations(R.style.window_right_in_right_out);
            mDialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            mDialogWindow.setGravity(Gravity.RIGHT);
        }
        return this;
    }

    /**
     * ??????????????? ???????????????  ???????????????
     *
     * @return
     */
    public BsDialog fromTop() {
        if (mDialogWindow != null) {
            fromTopToMiddle();
            mDialogWindow.setGravity(Gravity.CENTER | Gravity.TOP);
        }
        return this;
    }

    /**
     * ?????????????????????  ???????????????  ???????????????
     *
     * @return
     */
    public BsDialog fromTopToMiddle() {
        if (mDialogWindow != null) {
            mDialogWindow.setWindowAnimations(R.style.window_top_in_top_out);
            mDialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        return this;
    }


    /**
     * ????????????
     */
    public BsDialog fullScreen() {
        if (mDialogWindow != null) {
            WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
            wl.height = ViewGroup.LayoutParams.MATCH_PARENT;
            wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
            mDialog.onWindowAttributesChanged(wl);
        }
        return this;
    }


    public BsDialog setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        if (mDialogWindow != null) {
            mDialog.setOnKeyListener(onKeyListener);
        }
        return this;
    }

    /**
     * ????????????
     */
    public BsDialog fullWidth() {
        if (mDialogWindow != null) {
            WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
            wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
            mDialog.onWindowAttributesChanged(wl);
        }
        return this;
    }

    /**
     * ????????????
     */
    public BsDialog fullHeight() {
        if (mDialogWindow != null) {
            WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
            wl.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mDialog.onWindowAttributesChanged(wl);
        }
        return this;
    }

    /**
     * @param width  ??????????????????
     * @param height ??????????????????
     * @return
     */
    public BsDialog setWidthAndHeight(int width, int height) {
        if (mDialogWindow != null) {
            WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
            wl.width = width;
            wl.height = height;
            mDialog.onWindowAttributesChanged(wl);
        }
        return this;
    }

    /**
     * ??????
     */
    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            startOutAinma(true);
        }
    }

    /**
     * ????????????
     */
    public BsDialog setDialogDismissListener(OnDismissListener listener) {
        if (mDialog != null) {
            mDialog.setOnDismissListener(listener);
        }
        return this;
    }

    /**
     * ????????????
     */
    public BsDialog setOnCancelListener(OnCancelListener listener) {
        if (mDialog != null) {
            mDialog.setOnCancelListener(listener);
        }
        return this;
    }

    /**
     * ?????????????????????
     */
    public BsDialog setCancelAble(boolean cancel) {
        if (mDialog != null) {
            cancelable = cancel;
        }
        return this;
    }


    /**
     * ???????????????????????????????????????
     */
    public BsDialog setCanceledOnTouchOutside(boolean cancel) {
        if (mDialog != null) {
            cancelableOnTouchOutside = cancel;
            mDialog.setCanceledOnTouchOutside(cancel);
        }
        return this;
    }

    private void startOutAinma(final boolean isCancelable) {
        if (isCustomAnima) {// ???????????????
            if (cancelable || cancelableOnTouchOutside) {
                Animator animator = AnimatorHelper.getAnimator(mRootView, mOutAnimaType);
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
//                        LogUtils.d("BsDialog onAnimationStart ");
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
//                        LogUtils.d("BsDialog onAnimationEnd ");
                        mDialog.dismiss();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
//                        LogUtils.d("BsDialog onAnimationCancel ");

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
//                        LogUtils.d("BsDialog onAnimationRepeat ");

                    }
                });
                if (isCancelable) {//?????????????????? ??????true????????? ???????????????
                    animator.start();
                }
            }
        } else {
            if (isCancelable) {//?????????????????? ??????true????????? ???????????????
                mDialog.dismiss();
            }
        }
    }


}

