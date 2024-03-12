package pepse.world;

/**
 * Defines an interface for objects that observe the avatar's actions.
 */
public interface AvatarObserver {
    /**
     * Called when the avatar performs a jump action.
     */
    void updateWhenAvatarJumps();
}