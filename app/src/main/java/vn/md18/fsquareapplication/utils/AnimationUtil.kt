package vn.md18.fsquareapplication.utils

import android.animation.Animator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation


object AnimationUtil {
    fun startAnimation(
        view: View,
        animationId: Int,
        animationListener: Animation.AnimationListener? = null
    ) {
        val animation = AnimationUtils.loadAnimation(view.context, animationId)
        animation.repeatCount = Animation.INFINITE
        if (animationListener != null) {
            animation.setAnimationListener(animationListener)
        }
        view.startAnimation(animation)
    }

    fun scaleTo(view: View, offset: Float, duration: Long = 0) {
        view.animate().setDuration(duration).scaleX(offset).scaleY(offset).start()
    }


    fun fadeIn(view: View, duration: Long = Constant.DataConfig.DURATION_FADE_VIEW_IN_MILLIS, fromAlpha: Float = 0f, onAnimationEnd: (() -> Unit)? = null) {
        view.visibility = View.VISIBLE
        view.alpha = fromAlpha
        val propertyAnimator = view.animate().setDuration(duration).alpha(Constant.DataConfig.ALPHA_ENABLE_VIEW_VALUE)
        propertyAnimator.setListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator) {
                onAnimationEnd?.invoke()
            }

            override fun onAnimationStart(animation: Animator) {
                animation.start()

            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })
    }

    fun fadeOut(view: View, duration: Long = Constant.DataConfig.DURATION_FADE_VIEW_IN_MILLIS, onAnimationEnd: (() -> Unit)? = null) {
        view.alpha = 1f
        view.visibility = View.VISIBLE
        val propertyAnimator = view.animate().setDuration(duration).alpha(0f)
        propertyAnimator.setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator) {
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationStart(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {
                view.visibility = View.GONE
                propertyAnimator.setListener(null)
                onAnimationEnd?.invoke()
            }
        })
    }

    fun scaleFade(view: View, duration: Long = 1500) {
        val scaleAnim = ScaleAnimation(
            0.85f, 1f,
            0.85f, 1f,
            Animation.ABSOLUTE, 1f,
            Animation.RELATIVE_TO_SELF, 0f
        )
        scaleAnim.duration = duration
        scaleAnim.interpolator = AccelerateDecelerateInterpolator()
        scaleAnim.repeatMode = Animation.REVERSE
        scaleAnim.repeatCount = Animation.INFINITE
        view.startAnimation(scaleAnim)
    }

    fun scaleFadeItemCenter(view: View, duration: Long = 1500) {
        val scaleAnim = ScaleAnimation(
            0.88f, 1f,
            0.88f, 1f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 1f
        )
        scaleAnim.duration = duration
        scaleAnim.interpolator = AccelerateDecelerateInterpolator()
        scaleAnim.repeatMode = Animation.REVERSE
        scaleAnim.repeatCount = Animation.INFINITE
        view.startAnimation(scaleAnim)
    }

}
