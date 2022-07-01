package hwr.oop;

import hwr.oop.gameobjects.fixed.*;
import hwr.oop.gameobjects.versatile.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FixedObjectsTest {

    IIOHandler ioHandler;
    @BeforeEach
    void setUp() {
        ByteArrayInputStream input = new ByteArrayInputStream("TestResponse".getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        ioHandler = new IOHandler(input, new PrintStream(output));
    }

    @Nested
    class DoorTests{
        Door door;

        @BeforeEach
        void setUp() {
            door = new Door(ioHandler);
        }

        @Test
        void door_isFieldObject(){
            assertThat(door)
                    .isInstanceOf(FixedObject.class)
                    .isInstanceOf(Door.class);
        }

        @Test
        void door_getObjectIcon_iconIsPi() {
            String icon = door.getObjectIcon();
            assertThat(icon).isEqualTo("Π");
        }

        @Test
        void door_writeEventIOHandler_writesToIOHandler() {
            door.writeEventToIOHandler();

            assertThat(ioHandler.getOutputBuffer()).isEqualTo("\nYou don't have a Key to open the door.");
        }
    }
    @Nested
    class NothingTests{
        Nothing nothing;

        @BeforeEach
        void setUp() {
            nothing = new Nothing(ioHandler);
        }

        @Test
        void nothing_isFieldObject(){
            nothing = new Nothing(ioHandler);
            assertThat(nothing)
                    .isInstanceOf(FixedObject.class)
                    .isInstanceOf(Nothing.class);
        }

        @Test
        void nothing_getObjectIcon_iconIsUnderscore() {
            String icon = nothing.getObjectIcon();
            assertThat(icon).isEqualTo("_");
        }

        @Test
        void nothing_writeEventIOHandler_writesToIOHandler() {
            nothing.writeEventToIOHandler();

            assertThat(ioHandler.getOutputBuffer()).isEqualTo("\nNothing happens!");
        }
    }
    @Nested
    class NPCTests{
        NPC npc;
        Player player;

        @BeforeEach
        void setUp() {
            player = new Player(new Position(), 9);
            npc = new NPC(ioHandler, player);
        }

        @Test
        void npc_isFieldObject(){
            npc = new NPC(ioHandler, player);
            assertThat(npc)
                    .isInstanceOf(FixedObject.class)
                    .isInstanceOf(NPC.class);
        }

        @Test
        void npc_getObjectIcon_iconIsOmega() {
            String icon = npc.getObjectIcon();
            assertThat(icon).isEqualTo("Ω");
        }

        @Test
        void npc_writeEventIOHandlerFirstResponse_writesToIOHandler() {
            npc.writeEventToIOHandler();

            assertThat(ioHandler.getOutputBuffer()).isEqualTo("\nYep.");
        }

        @Test
        void npc_writeEventIOHandlerSecondResponse_writesToIOHandlerAndPlayerHasKey() {
            ByteArrayInputStream input = new ByteArrayInputStream("Give Key!".getBytes());
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            ioHandler = new IOHandler(input, new PrintStream(output));
            npc = new NPC(ioHandler, player);

            npc.writeEventToIOHandler();

            assertThat(ioHandler.getOutputBuffer()).isEqualTo("\nHere is your key!");

            boolean playerHasKey = player.hasKey();
            assertThat(playerHasKey).isTrue();
        }
    }
    @Nested
    class WallTests{

        Wall wall;

        @BeforeEach
        void setUp() {
            wall = new Wall(ioHandler);
        }

        @Test
        void wall_isFieldObject(){
            assertThat(wall)
                    .isInstanceOf(FixedObject.class)
                    .isInstanceOf(Wall.class);
        }

        @Test
        void wall_getObjectIcon_iconIsHashtag() {
            String icon = wall.getObjectIcon();
            assertThat(icon).isEqualTo("#");
        }

        @Test
        void wall_writeEventIOHandler_writesToIOHandler() {
            wall.writeEventToIOHandler();

            assertThat(ioHandler.getOutputBuffer()).isEqualTo("\nYou shouldn't be stuck in the wall!");
        }
    }

}
