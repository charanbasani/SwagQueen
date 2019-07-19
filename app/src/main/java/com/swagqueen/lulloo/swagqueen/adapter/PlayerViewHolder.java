package com.swagqueen.lulloo.swagqueen.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.RequestManager;
import com.google.android.exoplayer2.ui.PlayerView;
import com.swagqueen.lulloo.swagqueen.Model.MediaObject;
import com.swagqueen.lulloo.swagqueen.R;

public class PlayerViewHolder extends RecyclerView.ViewHolder {

  /**
   * below view have public modifier because
   * we have access PlayerViewHolder inside the ExoPlayerRecyclerView
   */
  public FrameLayout mediaContainer;
  public ImageView mediaCoverImage, volumeControl,fullscreen;
  public ProgressBar progressBar;
  public RequestManager requestManager;
  private TextView title;
  private View parent;
  private PlayerView playerView;

  public PlayerViewHolder(@NonNull View itemView) {
    super(itemView);
    parent = itemView;
    mediaContainer = itemView.findViewById(R.id.mediaContainer);
    mediaCoverImage = itemView.findViewById(R.id.ivMediaCoverImage);
    title = itemView.findViewById(R.id.tvTitle);

    progressBar = itemView.findViewById(R.id.progressBar);
    volumeControl = itemView.findViewById(R.id.ivVolumeControl);
    fullscreen = itemView.findViewById(R.id.fullscrren);

  }

  void onBind(MediaObject mediaObject, RequestManager requestManager) {
    this.requestManager = requestManager;
    parent.setTag(this);
    title.setText(mediaObject.getTitle());

    this.requestManager
        .load(mediaObject.getCoverUrl())
        .into(mediaCoverImage);
  }
}

