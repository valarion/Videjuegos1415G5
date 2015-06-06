package videjouegos1415g5.animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {

	public enum Direction{
		UP,DOWN,LEFT,RIGHT;
	}

    private int frameCount;                
    private int frameDelay;                 
    private int currentFrame;              
    private Direction animationDirection;        
     
    private int totalFrames;               
    private boolean stopped;
    private List<Frame> frames = new ArrayList<Frame>();

    public Animation(BufferedImage[] frames, int frameDelay) {
        this.frameDelay = frameDelay;
        this.stopped = true;

        for (int i = 0; i < frames.length; i++) {
            addFrame(frames[i], frameDelay);
        }

        this.frameCount = 0;
        this.frameDelay = frameDelay;
        this.currentFrame = 0;
        this.animationDirection = Direction.DOWN;
        this.totalFrames = this.frames.size();
        }
        
    public Animation(BufferedImage[] frames, int frameDelay, Direction dir) {
        this.frameDelay = frameDelay;
        this.stopped = true;

        for (int i = 0; i < frames.length; i++) {
            addFrame(frames[i], frameDelay);
        }

        this.frameCount = 0;
        this.frameDelay = frameDelay;
        this.currentFrame = 0;
        this.animationDirection = dir;
        this.totalFrames = this.frames.size();

    }


    public void start() {
        if (!stopped) {
            return;
        }

        if (frames.size() == 0) {
            return;
        }
        
        stopped = false;
    }

    public void stop() {
        if (frames.size() == 0) {
            return;
        }

        stopped = true;
    }

    public void restart() {
        if (frames.size() == 0) {
            return;
        }

        stopped = false;
        currentFrame = 0;
    }

    public void reset() {
        this.stopped = true;
        this.frameCount = 0;
        this.currentFrame = 0;
    }
    
    public boolean finalFrame() {
        return currentFrame == totalFrames - 1 && frameCount == frameDelay;
    }

    private void addFrame(BufferedImage frame, int duration) {
        if (duration <= 0) {
            throw new RuntimeException("Invalid duration: " + duration);
        }

        frames.add(new Frame(frame, duration));
        currentFrame = 0;
    }

    public BufferedImage getSprite() {
        return frames.get(currentFrame).getImg();
    }
    
    public Direction getAnimationDirection() {
		return animationDirection;
	}
    
    public void setAnimationDirection(Direction d) {
    	animationDirection = d;
	}

    public void tick() {
        if (!stopped) {
            frameCount++;

            if (frameCount > frameDelay) {
                frameCount = 0;
                currentFrame += 1;

                if (currentFrame > totalFrames - 1) {
                    currentFrame = 0;
                }
                else if (currentFrame < 0) {
                    currentFrame = totalFrames - 1;
                }
            }
        }

    }

}
//
//import java.awt.Graphics2D;
//import java.awt.Image;
//import java.awt.Rectangle;
//import java.util.NavigableSet;
//import java.util.TreeSet;
//
//public class Animation {
//	protected NavigableSet<Frame> frames;
//	protected long animationlength;
//	
//	public Animation() {
//		frames = new TreeSet<Frame>();
//		animationlength = 0;
//	}
//	
//	public void addFrame(Image img, long time) {
//		frames.add(new Frame(img,time));
//		animationlength += time;
//	}
//
//	public void draw(Graphics2D g, Rectangle position, long state) {
//		Image sprite = frames.floor(new Frame(state%animationlength)).getImg();
//		g.drawImage(sprite, position.x, position.y, null); // TODO draw in position
//	}
//}
