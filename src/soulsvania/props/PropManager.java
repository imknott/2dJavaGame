//package soulsvania.props;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.io.*;
//
//public class PropManager {
//
//    GamePanel gp;
//
//    Prop[] prop;
//    int propNumber[];
//
//    public PropManager(GamePanel gp) {
//        this.gp = gp;
//        this.prop = new Prop[10];
//        //using my map txt file i will be storing each number into this array to be rendered to the screen.
//
//        this.propNumber = new int[gp.maxScreenColumn];
//        getPropImage();
//    }
//
//    public void getPropImage() {
//        try {
//
//            //This is how I will specify prop images
//            prop[0] = new Prop();
//            prop[0].image = ImageIO.read(getClass().getResourceAsStream("/soulsvania.props/tree00.png"));
//            prop[1] = new Prop();
//            prop[1].image = ImageIO.read(getClass().getResourceAsStream("/soulsvania.props/tree01.png"));
//            prop[2] = new Prop();
//            prop[2].image = ImageIO.read(getClass().getResourceAsStream("/soulsvania.props/statue00.png"));
//            prop[3] = new Prop();
//            prop[3].image = ImageIO.read(getClass().getResourceAsStream("/soulsvania.props/mossywall00.png"));
//            prop[4] = new Prop();
//            prop[4].image = ImageIO.read(getClass().getResourceAsStream("/soulsvania.props/mossywall01.png"));
//            prop[5] = new Prop();
//            prop[5].image = ImageIO.read(getClass().getResourceAsStream("/soulsvania.props/wallTile00.png"));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //used to get the map prop file for when player hits the edge of the map. which we will pass a number that will be ref.
////    public String getPropMapFile(){
////
////    }
//    public void loadPropMap(){
//        try {
//            InputStream mapStream = getClass().getResourceAsStream("/maps/propMap00.txt");
//            BufferedReader br = new BufferedReader(new InputStreamReader(mapStream));
//
//            int x = 0;
//
//            while (x < 16) {
//                String line = br.readLine();
//                String[] numbers = line.split("");
//                int num = Integer.parseInt(numbers[x]);
//                propNumber[x] = num;
//                x++;
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    //want to specify soulsvania.states that our program will switch through.
//
//    public void drawProp(Graphics2D g2) {
//        int col = 0;
//        int x =0;
//        int y = 450;
//        int propNum = propNumber[col];
//        while (x < gp.screenWidth){
//            g2.drawImage(prop[propNum].image, x, y, 80, 80, null);
//            x += 48;
//            col++;
//        }

//This class was originally created to handle adding soulsvania.props to the map but I never got around to adding them.

