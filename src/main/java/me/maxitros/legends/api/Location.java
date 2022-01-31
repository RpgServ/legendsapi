package me.maxitros.legends.api;

import me.maxitros.legends.api.weapon.IStabbing;
import net.minecraft.block.Block;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class Location implements Cloneable {
    private World world;
    private double x;
    private double y;
    private double z;
    private float pitch;
    private float yaw;

    /**
     * Constructs a new Location with the given coordinates
     *
     * @param world The world in which this location resides
     * @param x The x-coordinate of this new location
     * @param y The y-coordinate of this new location
     * @param z The z-coordinate of this new location
     */
    public Location(final World world, final double x, final double y, final double z) {
        this(world, x, y, z, 0, 0);
    }

    /**
     * Constructs a new Location with the given coordinates and direction
     *
     * @param world The world in which this location resides
     * @param x The x-coordinate of this new location
     * @param y The y-coordinate of this new location
     * @param z The z-coordinate of this new location
     * @param yaw The absolute rotation on the x-plane, in degrees
     * @param pitch The absolute rotation on the y-plane, in degrees
     */
    public Location(final World world, final double x, final double y, final double z, final float yaw, final float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    /**
     * Sets the world that this location resides in
     *
     * @param world New world that this location resides in
     */
    public void setWorld(World world) {
        this.world = world;
    }

    /**
     * Gets the world that this location resides in
     *
     * @return World that contains this location
     */
    public World getWorld() {
        return world;
    }

    /**
     * Gets the chunk at the represented location
     *
     * @return Chunk at the represented location
     */
    

    /**
     * Gets the block at the represented location
     *
     * @return Block at the represented location
     */
  

    /**
     * Sets the x-coordinate of this location
     *
     * @param x X-coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets the x-coordinate of this location
     *
     * @return x-coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the floored value of the X component, indicating the block that
     * this location is contained with.
     *
     * @return block X
     */
    public int getBlockX() {
        return locToBlock(x);
    }

    /**
     * Sets the y-coordinate of this location
     *
     * @param y y-coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gets the y-coordinate of this location
     *
     * @return y-coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Gets the floored value of the Y component, indicating the block that
     * this location is contained with.
     *
     * @return block y
     */
    public int getBlockY() {
        return locToBlock(y);
    }

    /**
     * Sets the z-coordinate of this location
     *
     * @param z z-coordinate
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * Gets the z-coordinate of this location
     *
     * @return z-coordinate
     */
    public double getZ() {
        return z;
    }

    /**
     * Gets the floored value of the Z component, indicating the block that
     * this location is contained with.
     *
     * @return block z
     */
    public int getBlockZ() {
        return locToBlock(z);
    }

    /**
     * Sets the yaw of this location, measured in degrees.
     * <ul>
     * <li>A yaw of 0 or 360 represents the positive z direction.
     * <li>A yaw of 180 represents the negative z direction.
     * <li>A yaw of 90 represents the negative x direction.
     * <li>A yaw of 270 represents the positive x direction.
     * </ul>
     * Increasing yaw values are the equivalent of turning to your
     * right-facing, increasing the scale of the next respective axis, and
     * decreasing the scale of the previous axis.
     *
     * @param yaw new rotation's yaw
     */
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    /**
     * Gets the yaw of this location, measured in degrees.
     * <ul>
     * <li>A yaw of 0 or 360 represents the positive z direction.
     * <li>A yaw of 180 represents the negative z direction.
     * <li>A yaw of 90 represents the negative x direction.
     * <li>A yaw of 270 represents the positive x direction.
     * </ul>
     * Increasing yaw values are the equivalent of turning to your
     * right-facing, increasing the scale of the next respective axis, and
     * decreasing the scale of the previous axis.
     *
     * @return the rotation's yaw
     */
    public float getYaw() {
        return yaw;
    }

    /**
     * Sets the pitch of this location, measured in degrees.
     * <ul>
     * <li>A pitch of 0 represents level forward facing.
     * <li>A pitch of 90 represents downward facing, or negative y
     *     direction.
     * <li>A pitch of -90 represents upward facing, or positive y direction.
     * <ul>
     * Increasing pitch values the equivalent of looking down.
     *
     * @param pitch new incline's pitch
     */
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    /**
     * Gets the pitch of this location, measured in degrees.
     * <ul>
     * <li>A pitch of 0 represents level forward facing.
     * <li>A pitch of 90 represents downward facing, or negative y
     *     direction.
     * <li>A pitch of -90 represents upward facing, or positive y direction.
     * <ul>
     * Increasing pitch values the equivalent of looking down.
     *
     * @return the incline's pitch
     */
    public float getPitch() {
        return pitch;
    }

    /**
     * Gets a unit-Vec3d pointing in the direction that this Location is
     * facing.
     *
     * @return a Vec3d pointing the direction of this location's {@link
     *     #getPitch() pitch} and {@link #getYaw() yaw}
     */
    public Vec3d getDirection() {
       

        double rotX = this.getYaw();
        double rotY = this.getPitch();
        
      

        double xz = Math.cos(Math.toRadians(rotY));
        Vec3d Vec3d = new Vec3d(-xz * Math.sin(Math.toRadians(rotX)), -Math.sin(Math.toRadians(rotY)), xz * Math.cos(Math.toRadians(rotX)));

        return Vec3d;
    }

    /**
     * Sets the {@link #getYaw() yaw} and {@link #getPitch() pitch} to point
     * in the direction of the Vec3d.
     */
    public Location setDirection(Vec3d Vec3d) {
        /*
         * Sin = Opp / Hyp
         * Cos = Adj / Hyp
         * Tan = Opp / Adj
         *
         * x = -Opp
         * z = Adj
         */
        final double _2PI = 2 * Math.PI;
        final double x = Vec3d.x;
        final double z = Vec3d.z;

        if (x == 0 && z == 0) {
            pitch = Vec3d.y > 0 ? -90 : 90;
            return this;
        }

        double theta = Math.atan2(-x, z);
        yaw = (float) Math.toDegrees((theta + _2PI) % _2PI);

        double x2 = NumberConversions.square(x);
        double z2 = NumberConversions.square(z);
        double xz = Math.sqrt(x2 + z2);
        pitch = (float) Math.toDegrees(Math.atan(-Vec3d.y / xz));

        return this;
    }

    /**
     * Adds the location by another.
     *
     * @see Vec3d
     * @param vec The other location
     * @return the same location
     * @throws IllegalArgumentException for differing worlds
     */
    public Location add(Location vec) {
        if (vec == null || vec.getWorld() != getWorld()) {
            throw new IllegalArgumentException("Cannot add Locations of differing worlds");
        }

        x += vec.x;
        y += vec.y;
        z += vec.z;
        return this;
    }

    /**
     * Adds the location by a Vec3d.
     *
     * @see Vec3d
     * @param vec Vec3d to use
     * @return the same location
     */
    public Location add(Vec3d vec) {
        this.x += vec.x;
        this.y += vec.y;
        this.z += vec.z;
        return this;
    }

    /**
     * Adds the location by another. Not world-aware.
     *
     * @see Vec3d
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @return the same location
     */
    public Location add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    /**
     * Subtracts the location by another.
     *
     * @see Vec3d
     * @param vec The other location
     * @return the same location
     * @throws IllegalArgumentException for differing worlds
     */
    public Location subtract(Location vec) {
        if (vec == null || vec.getWorld() != getWorld()) {
            throw new IllegalArgumentException("Cannot add Locations of differing worlds");
        }

        x -= vec.x;
        y -= vec.y;
        z -= vec.z;
        return this;
    }

    /**
     * Subtracts the location by a Vec3d.
     *
     * @see Vec3d
     * @param vec The Vec3d to use
     * @return the same location
     */
    public Location subtract(Vec3d vec) {
        this.x -= vec.x;
        this.y -= vec.y;
        this.z -= vec.z;
        return this;
    }

    /**
     * Subtracts the location by another. Not world-aware and
     * orientation independent.
     *
     * @see Vec3d
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @return the same location
     */
    public Location subtract(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    /**
     * Gets the magnitude of the location, defined as sqrt(x^2+y^2+z^2). The
     * value of this method is not cached and uses a costly square-root
     * function, so do not repeatedly call this method to get the location's
     * magnitude. NaN will be returned if the inner result of the sqrt()
     * function overflows, which will be caused if the length is too long. Not
     * world-aware and orientation independent.
     *
     * @see Vec3d
     * @return the magnitude
     */
    public double length() {
        return Math.sqrt(NumberConversions.square(x) + NumberConversions.square(y) + NumberConversions.square(z));
    }

    /**
     * Gets the magnitude of the location squared. Not world-aware and
     * orientation independent.
     *
     * @see Vec3d
     * @return the magnitude
     */
    public double lengthSquared() {
        return NumberConversions.square(x) + NumberConversions.square(y) + NumberConversions.square(z);
    }

    /**
     * Get the distance between this location and another. The value of this
     * method is not cached and uses a costly square-root function, so do not
     * repeatedly call this method to get the location's magnitude. NaN will
     * be returned if the inner result of the sqrt() function overflows, which
     * will be caused if the distance is too long.
     *
     * @see Vec3d
     * @param o The other location
     * @return the distance
     * @throws IllegalArgumentException for differing worlds
     */
    public double distance(Location o) {
        return Math.sqrt(distanceSquared(o));
    }

    /**
     * Get the squared distance between this location and another.
     *
     * @see Vec3d
     * @param o The other location
     * @return the distance
     * @throws IllegalArgumentException for differing worlds
     */
    public double distanceSquared(Location o) {
        if (o == null) {
            throw new IllegalArgumentException("Cannot measure distance to a null location");
        } else if (o.getWorld() == null || getWorld() == null) {
            throw new IllegalArgumentException("Cannot measure distance to a null world");
        } else if (o.getWorld() != getWorld()) {
            throw new IllegalArgumentException("Cannot measure distance between " + getWorld().getProviderName() + " and " + o.getWorld().getProviderName());
        }

        return NumberConversions.square(x - o.x) + NumberConversions.square(y - o.y) + NumberConversions.square(z - o.z);
    }

    /**
     * Performs scalar multiplication, multiplying all components with a
     * scalar. Not world-aware.
     *
     * @param m The factor
     * @see Vec3d
     * @return the same location
     */
    public Location multiply(double m) {
        x *= m;
        y *= m;
        z *= m;
        return this;
    }

    /**
     * Zero this location's components. Not world-aware.
     *
     * @see Vec3d
     * @return the same location
     */
    public Location zero() {
        x = 0;
        y = 0;
        z = 0;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;

        if (this.world != other.world && (this.world == null || !this.world.equals(other.world))) {
            return false;
        }
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.z)) {
            return false;
        }
        if (Float.floatToIntBits(this.pitch) != Float.floatToIntBits(other.pitch)) {
            return false;
        }
        if (Float.floatToIntBits(this.yaw) != Float.floatToIntBits(other.yaw)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;

        hash = 19 * hash + (this.world != null ? this.world.hashCode() : 0);
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
        hash = 19 * hash + Float.floatToIntBits(this.pitch);
        hash = 19 * hash + Float.floatToIntBits(this.yaw);
        return hash;
    }

    @Override
    public String toString() {
        return "Location{" + "world=" + world + ",x=" + x + ",y=" + y + ",z=" + z + ",pitch=" + pitch + ",yaw=" + yaw + '}';
    }

    /**
     * Constructs a new {@link Vec3d} based on this Location
     *
     * @return New Vec3d containing the coordinates represented by this
     *     Location
     */
    public Vec3d toVec3d() {
        return new Vec3d(x, y, z);
    }

    @Override
    public Location clone() {
        try {
            return (Location) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    /**
     * Safely converts a double (location coordinate) to an int (block
     * coordinate)
     *
     * @param loc Precise coordinate
     * @return Block coordinate
     */
    public static int locToBlock(double loc) {
        return NumberConversions.floor(loc);
    }
}
