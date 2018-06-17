import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.api.methods.send.*;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class bot extends TelegramLongPollingBot {

    private long actual_chatId;

    private int ordercda;
    //-------GOD-------------
    private int ordergod;
    private int ordergod2;
    private String godName;
    private String godName2;
    private Integer godID2;
    private Integer godID;
    private Long godChatID2;
    private Long godChatID;
    //-----------------------
    //Main group
    private String mainGroupName = null;
    private Long mainGroupID = null;
    //User selected
    private String userSelectedName = null;
    private Long userSelectedID = null;

    //hate word (person)
    private String hate = null;
    //
    int randomNumRE;

    private int typeMsg; //1 - MSG | 2 - PHOTO | 3 - VIDEO | 4 - FILE | 6 - STICKER

    private String _photo;
    private Long _chatid;
    private String _text;
    private Integer _reply;
    private File _document;
    private String _sticker;
    private String _filename;

    public SendMessage s_msg(String msgText, Long msgChatid, Integer msgReply) {
        SendMessage s_msg = new SendMessage();
        s_msg.setText(msgText);
        s_msg.setChatId(msgChatid);
        s_msg.setReplyToMessageId(msgReply);
        return s_msg;
    }

    public SendPhoto s_pht(String phtCapton, String phtPhoto, Long phtChatid, Integer phtReply) {
        SendPhoto s_pht = new SendPhoto();
        s_pht.setCaption(phtCapton);
        s_pht.setPhoto(phtPhoto);
        s_pht.setChatId(phtChatid);
        s_pht.setReplyToMessageId(phtReply);
        return s_pht;
    }

    public SendVideo s_vid(String vidCapton, String vidPhoto, Long vidChatid, Integer vidReply) {
        SendVideo s_vid = new SendVideo();
        s_vid.setCaption(vidCapton);
        s_vid.setVideo(vidPhoto);
        s_vid.setChatId(vidChatid);
        s_vid.setReplyToMessageId(vidReply);
        return s_vid;
    }

    public SendDocument s_doc(String docCaption, File fileDocumment, Long docChatid, Integer docReply) {
        SendDocument s_doc = new SendDocument();
        s_doc.setCaption(docCaption);
        s_doc.setChatId(docChatid);
        s_doc.setReplyToMessageId(docReply);
        s_doc.setNewDocument(fileDocumment);
        return s_doc;
    }

    public SendSticker s_sti(Long stiChatid, String stiSticker) {
        SendSticker s_sti = new SendSticker();
        s_sti.setChatId(stiChatid);
        s_sti.setSticker(stiSticker);
        return s_sti;
    }

    /*
    Sticker IDs Repository
        "CAADBAADAQADfzg3HWAWY4VyPGK3Ag" - Zero Two LolliPop
        "CAADBAADxgYAAl6KcQABhIOJZu1-RfUC" - k dise tontopoia tuh muerto
        "CAADAQAD5QADWF7QDln-Rovfrc4LAg" - aw shit nigga
    */

    private String Command(String command) {
        StringBuffer output = new StringBuffer();
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }
    //Users Registry
    private ArrayList<String> UsersArray = new ArrayList<String>();
    private ArrayList<Integer> UsersIDArray = new ArrayList<Integer>();
    public String logUsers() {
        String output = "";
        Integer integer_output = null;
        int number = 0;
        for (String x : UsersArray) {
            integer_output = UsersIDArray.get(number);
            output = output + "\n" + x + "\nID: " + integer_output;
            number++;
        }

        return output;
    }
    //Groups Registry
    private ArrayList<String> GroupsArray = new ArrayList<String>();
    private ArrayList<Long> GroupsIDArray = new ArrayList<Long>();
    public String logGroups() {
        String output = "";
        Long long_output = null;
        int number = 0;
        for (String x : GroupsArray) {
            long_output = GroupsIDArray.get(number);
            output = output + "\n" + x + "\nID: " + long_output;
            number++;
        }

        return output;
    }
    //Main Group Users Registry
    private ArrayList<String> GroupUsersArray = new ArrayList<String>();
    private ArrayList<Integer> GroupUsersIDArray = new ArrayList<Integer>();
    public String logGroupUsers(String a) {
        String output = "Grupo: " + a;
        Integer integer_output = null;
        int number = 0;
        for (String x : GroupUsersArray) {
            integer_output = GroupUsersIDArray.get(number);
            output = output + "\n" + x + "\nID: " + integer_output;
            number++;
        }

        return output;
    }

    public void onUpdateReceived(Update update) {

        log log = new log();

        if (actual_chatId == update.getMessage().getChatId()) {
            System.out.println(" [" + update.getMessage().getFrom().getUserName() + "]: " + update.getMessage().getText());
            log.log(update.getMessage().getFrom().getUserName(), update.getMessage().getFrom().getId(), update.getMessage().getChat().getTitle(), update.getMessage().getText(), 0);
        } else {
            if (update.getMessage().getChat().isUserChat()) {
                System.out.println("#User: " + update.getMessage().getFrom().getUserName());
                log.log(update.getMessage().getFrom().getUserName(), update.getMessage().getFrom().getId(), update.getMessage().getChat().getTitle(), update.getMessage().getText(), 0);
                //register chats in array
                if (!UsersIDArray.contains(update.getMessage().getFrom().getId())) {
                    UsersArray.add("@" + update.getMessage().getFrom().getUserName());
                    UsersIDArray.add(update.getMessage().getFrom().getId());
                }
            } else if (update.getMessage().getChat().isGroupChat()) {
                System.out.println("#Group: " + update.getMessage().getChat().getTitle());
                log.log(update.getMessage().getFrom().getUserName(), update.getMessage().getFrom().getId(), update.getMessage().getChat().getTitle(), update.getMessage().getText(), 1);

                if (!GroupsIDArray.contains(update.getMessage().getChat().getId())) {
                    GroupsArray.add(update.getMessage().getChat().getTitle());
                    GroupsIDArray.add(update.getMessage().getChat().getId());
                }

            } else if (update.getMessage().getChat().isChannelChat()) {
                System.out.println("#Channel: " + update.getMessage().getChat().getTitle());
                log.log(update.getMessage().getFrom().getUserName(), update.getMessage().getFrom().getId(), update.getMessage().getChat().getTitle(), update.getMessage().getText(), 2);

            } else if (update.getMessage().getChat().isSuperGroupChat()) {
                System.out.println("#S-Group: " + update.getMessage().getChat().getTitle());
                log.log(update.getMessage().getFrom().getUserName(), update.getMessage().getFrom().getId(), update.getMessage().getChat().getTitle(), update.getMessage().getText(), 3);

            } else {
                System.out.println("#WTF: " + update.getMessage().getChat().getTitle());
            }
            System.out.println(" [" + update.getMessage().getFrom().getUserName() + "]: " + update.getMessage().getText());
            actual_chatId = update.getMessage().getChatId();
        }

        if (update.getMessage().getChatId().equals(mainGroupID)) {
            if (!GroupUsersIDArray.contains(update.getMessage().getFrom().getId())) {
                GroupUsersArray.add("@" + update.getMessage().getFrom().getUserName());
                GroupUsersIDArray.add(update.getMessage().getFrom().getId());
            }
        }

        //....................................................
        //Clear message
        ordercda = 0;
        typeMsg = 0;
        _text = null;
        _photo = null;
        _document = null;
        _reply = null;
        //....................................................
        if (update.getMessage().getSticker() == null) {
            if ("omae wa mou shindeiru".equals(update.getMessage().getText())) {
                typeMsg = 3;
                _text = null;
                _photo = "https://i.makeagif.com/media/2-21-2015/RDVwim.gif";
                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();
            } else if ("nani".equals(update.getMessage().getText()) || "NANI".equals(update.getMessage().getText()) || "Nani".equals(update.getMessage().getText())) {
                typeMsg = 3;
                _text = null;
                _photo = "https://pa1.narvii.com/6715/4c6e81d7a5f2f5f642f40cf23a3cfe19881cb76e_hq.gif";
                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();
            } else if ("aaa perro traes el omnitrix".equals(update.getMessage().getText()) || "Aaa perro traes el omnitrix".equals(update.getMessage().getText())) {
                typeMsg = 2;
                _text = null;
                _photo = "https://s3.amazonaws.com/glr-fileserver/Larepublica/2018/03/04/facebook-omnitrix7-1520172526.jpg";
                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();
            } else if ("what".equals(update.getMessage().getText()) || "WHAT".equals(update.getMessage().getText()) || "What".equals(update.getMessage().getText())) {
                typeMsg = 3;
                _text = null;
                _photo = "https://media.giphy.com/media/5QTLUC40nxc1lobJGp/giphy.gif";
                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();
            } else if ("/start".equals(update.getMessage().getText())) {
                if (update.getMessage().getChat().isUserChat()) {
                    typeMsg = 1;
                    _text = EmojiParser.parseToUnicode("Hola " + update.getMessage().getFrom().getFirstName() + ", puedes contarme tus secretos :wink:");
                    _photo = null;
                    _chatid = update.getMessage().getChatId();
                    _reply = null;
                }
            } else if (update.getMessage().getText().contains("y la cosa suena ra") || update.getMessage().getText().contains("Y la cosa suena ra")) {
                typeMsg = 1;
                _text = "scooby doo papa";
                _photo = null;
                _chatid = update.getMessage().getChatId();
                _reply = null;
            } else if (update.getMessage().getText().contains("cooby doo papa") || update.getMessage().getText().contains("cooby doo pa pa")) {
                typeMsg = 1;
                _photo = null;
                _chatid = update.getMessage().getChatId();
                _reply = null;

                int rN = ThreadLocalRandom.current().nextInt(0, 4);
                while (rN == randomNumRE) {
                    rN = ThreadLocalRandom.current().nextInt(0, 4);
                }
                randomNumRE = rN;

                if (rN == 0) {
                    _text = "y el pum pum";
                } else if (rN == 1) {
                    _text = "y el pum pum pum";
                } else if (rN == 2) {
                    _text = "y el pum pum pum pum";
                } else {
                    _text = "y el pum pum pum pum pum";
                }

            } else if (update.getMessage().getText().contains("mariabot") || update.getMessage().getText().contains("Mariabot") || update.getMessage().getText().contains("MariaBot") || update.getMessage().getText().contains("maria bot") || update.getMessage().getText().contains("Maria bot") || update.getMessage().getText().contains("Maria Bot")) {

                typeMsg = 1;
                _chatid = update.getMessage().getChatId();
                _reply = null;

                if (hate != null && update.getMessage().getText().contains(hate)) {
                    int randomNum = ThreadLocalRandom.current().nextInt(0, 4);
                    while (randomNum == randomNumRE) {
                        randomNum = ThreadLocalRandom.current().nextInt(0, 4);
                    }
                    randomNumRE = randomNum;

                    typeMsg = 1;
                    _chatid = update.getMessage().getChatId();
                    _photo = null;
                    _reply = null;

                    if (randomNum == 0) {
                        _text = EmojiParser.parseToUnicode("No tengo nada que hablar con un gilipollas como " + hate);
                    } else if (randomNum == 1) {
                        _text = EmojiParser.parseToUnicode("A " + hate + " no se merece ni que le hable ");
                    } else if (randomNum == 2) {
                        _text = EmojiParser.parseToUnicode("Que me da igual todo lo que tenga que ver con esa persona \uD83D\uDE44");
                    } else {
                        _text = EmojiParser.parseToUnicode("Para que mencionas al subnormal ese¿?");
                    }
                } else if (update.getMessage().getText().contains("si o no")) {
                    typeMsg = 1;
                    _chatid = update.getMessage().getChatId();
                    _reply = null;

                    int rNx = ThreadLocalRandom.current().nextInt(0, 6);
                    while (rNx == randomNumRE) {
                        rNx = ThreadLocalRandom.current().nextInt(0, 6);
                    }
                    randomNumRE = rNx;

                    if (rNx == 0) {
                        _text = EmojiParser.parseToUnicode("Seh :smirk:");
                    } else if (rNx == 1) {
                        _text = "nooooooo";
                    } else if (rNx == 2) {
                        _text = "ps si";
                    } else if (rNx == 3) {
                        _text = "ps no";
                    } else if (rNx == 4) {
                        _text = "Claro";
                    } else if (rNx == 5) {
                        _text = EmojiParser.parseToUnicode("mmm :thinking: nop");
                    } else {
                        _text = "puede \uD83D\uDE44";
                    }
                } else if (update.getMessage().getText().contains("tirar dados")) {
                    typeMsg = 1;
                    _chatid = update.getMessage().getChatId();
                    _reply = null;

                    int rNx = ThreadLocalRandom.current().nextInt(0, 100);
                    while (rNx == randomNumRE) {
                        rNx = ThreadLocalRandom.current().nextInt(0, 100);
                    }
                    randomNumRE = rNx;

                    _text = EmojiParser.parseToUnicode(":game_die: " + update.getMessage().getFrom().getFirstName() + " tira dados y saca " + rNx + " (0-100)");

                } else if (update.getMessage().getText().contains("que prefieres, ") || update.getMessage().getText().contains("que hago, ") || update.getMessage().getText().contains("quien prefieres, ") || update.getMessage().getText().contains("ariabot, ") || update.getMessage().getText().contains("aria bot, ") || update.getMessage().getText().contains("ue es mejor, ") || update.getMessage().getText().contains("elige, ") && update.getMessage().getText().contains(" o ")) {
                    String message = update.getMessage().getText();
                    String[] parts = message.split(", ");
                    String part1 = parts[0];
                    String part2 = parts[1];


                    String[] xparts = part2.split(" o ");
                    String xpart1 = xparts[0]; //eleccion 1
                    String xpart2 = xparts[1]; //eleccion 2

                    String realxpart1 = xpart1;
                    String realxpart2 = xpart2;
                    if (update.getMessage().getText().contains("¿")) {
                        realxpart1 = xpart1.replace("¿", "");
                    }
                    if (update.getMessage().getText().contains("?")) {
                        realxpart2 = xpart2.replace("?", "");
                    }

                    int rN = ThreadLocalRandom.current().nextInt(0, 2);
                    while (rN == randomNumRE) {
                        rN = ThreadLocalRandom.current().nextInt(0, 2);
                    }
                    randomNumRE = rN;

                    String choice;
                    if (rN == 0) {
                        choice = realxpart1;
                    } else {
                        choice = realxpart2;
                    }
                    int rN1 = ThreadLocalRandom.current().nextInt(0, 7);
                    while (rN1 == randomNumRE) {
                        rN1 = ThreadLocalRandom.current().nextInt(0, 7);
                    }
                    randomNumRE = rN1;
                    if (rN1 == 0) {
                        _text = "es mejor " + choice;
                    } else if (rN1 == 1) {
                        _text = choice + " obviamente...";
                    } else if (rN1 == 2) {
                        _text = "yo creo que " + choice + " es mejor uwu";
                    } else if (rN1 == 3) {
                        _text = choice + "? Keeeeeee";
                    } else if (rN1 == 4) {
                        _text = "Claramente " + choice;
                    } else if (rN1 == 5) {
                        _text = "puesssss " + choice + " mismo yokese";
                    } else if (rN1 == 6) {
                        _text = choice + " no mola...";
                    } else {
                        _text = "prefiero " + choice;
                    }

                } else if (update.getMessage().getText().contains("version")) {
                    typeMsg = 1;
                    _chatid = update.getMessage().getChatId();
                    _reply = update.getMessage().getMessageId();
                    _text = "version actual: 1.9.2";
                } else if (update.getMessage().getText().contains("dime algo")) {
                    typeMsg = 5;
                    _chatid = update.getMessage().getChatId();
                    _sticker = "CAADBAADAQADfzg3HWAWY4VyPGK3Ag";
                } else if (update.getMessage().getText().contains("ataca")) {
                    typeMsg = 1;
                    _photo = null;
                    _chatid = update.getMessage().getChatId();
                    _reply = null;

                    int rN = ThreadLocalRandom.current().nextInt(0, 6);
                    while (rN == randomNumRE) {
                        rN = ThreadLocalRandom.current().nextInt(0, 6);
                    }
                    randomNumRE = rN;

                    if (rN == 0) {
                        _text = "Tu waifu es un trap";
                    } else if (rN == 1) {
                        _text = "Tu waifu no existe";
                    } else if (rN == 2) {
                        _text = "Tu waifu no es real";
                    } else if (rN == 3) {
                        _text = "Tu waifu es pokemon";
                    } else if (rN == 4) {
                        _text = "Eres tan feo que ni los ghouls te quieren comer";
                    } else {
                        _text = "Tu waifu es una kk";
                    }
                } else if (update.getMessage().getText().contains("que opinas")) {
                    int randomNum = ThreadLocalRandom.current().nextInt(0, 4);
                    while (randomNum == randomNumRE) {
                        randomNum = ThreadLocalRandom.current().nextInt(0, 4);
                    }
                    randomNumRE = randomNum;

                    typeMsg = 1;
                    _chatid = update.getMessage().getChatId();
                    _photo = null;
                    _reply = null;

                    if (randomNum == 0) {
                        _text = EmojiParser.parseToUnicode("que es una kk :no_mouth:");
                    } else if (randomNum == 1) {
                        _text = EmojiParser.parseToUnicode("tiene razon " + update.getMessage().getFrom().getFirstName() + " obviamente");
                    } else if (randomNum == 2) {
                        _text = EmojiParser.parseToUnicode("que me da igual \uD83D\uDE44");
                    } else {
                        _text = EmojiParser.parseToUnicode("mmm :thinking:");
                    }
                } else {

                    int randomNum = ThreadLocalRandom.current().nextInt(0, 14);
                    while (randomNum == randomNumRE) {
                        randomNum = ThreadLocalRandom.current().nextInt(0, 14);
                    }
                    randomNumRE = randomNum;

                    //all phrases
                    if (update.getMessage().getText().contains("put") || update.getMessage().getText().contains("tont") || update.getMessage().getText().contains("inutil") || update.getMessage().getText().contains("subnormal") || update.getMessage().getText().contains("estupid") || update.getMessage().getText().contains("maricon") || update.getMessage().getText().contains("cabron") || update.getMessage().getText().contains("idiota") || update.getMessage().getText().contains("baka") || update.getMessage().getText().contains("feo") || update.getMessage().getText().contains("fea") || update.getMessage().getText().contains("muert") || update.getMessage().getText().contains("baka")) {
                        if (randomNum == 1) {
                            _text = "eh, sin insultar xd";
                        } else if (randomNum == 2) {
                            _text = "no digas palabrotas";
                        } else if (randomNum == 3) {
                            _text = ":c";
                        } else if (randomNum == 4) {
                            _text = "khe pesaos che";
                        } else if (randomNum == 5 || randomNum == 5) {
                            _text = "acho";
                        } else if (randomNum == 7) {
                            _text = "tonto tu";
                        } else if (randomNum == 8) {
                            _text = "te saco la navaja";
                        } else if (randomNum == 9) {
                            _text = "vas a morir";
                        } else if (randomNum == 10) {
                            _text = "no me hables asi";
                        } else if (randomNum == 11) {
                            _text = EmojiParser.parseToUnicode("enserio... :neutral_face:");
                        } else if (randomNum == 12) {
                            typeMsg = 5;
                            _sticker = "CAADBAADxgYAAl6KcQABhIOJZu1-RfUC";
                        } else if (randomNum == 13) {
                            typeMsg = 5;
                            _sticker = "CAADAQAD5QADWF7QDln-Rovfrc4LAg";
                        } else {
                            _text = "vaya tela...";
                        }
                    } else {
                        if (randomNum == 1) {
                            _text = "ehhhh";
                        } else if (randomNum == 2) {
                            _text = "que pasa aqui?";
                        } else if (randomNum == 3) {
                            _text = EmojiParser.parseToUnicode(":thinking:");
                        } else if (randomNum == 4) {
                            _text = "khe pesaos";
                        } else if (randomNum == 5 || randomNum == 5) {
                            _text = "acho";
                        } else if (randomNum == 7) {
                            _text = "achoo";
                        } else if (randomNum == 8) {
                            _text = "que dices";
                        } else if (randomNum == 9) {
                            _text = ":3";
                        } else if (randomNum == 10) {
                            _text = ":c";
                        } else if (randomNum == 11) {
                            _text = EmojiParser.parseToUnicode(":neutral_face:");
                        } else if (randomNum == 12) {
                            _text = EmojiParser.parseToUnicode("Ssshhhhh");
                        } else if (randomNum == 13) {
                            _text = EmojiParser.parseToUnicode("Diuuuuu");
                        } else {
                            _text = "uwu";
                        }
                    }
                }

            } else if (update.getMessage().getText().contains(":(")) {
                typeMsg = 1;
                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();

                int randomNum = ThreadLocalRandom.current().nextInt(0, 4);
                while (randomNum == randomNumRE) {
                    randomNum = ThreadLocalRandom.current().nextInt(0, 4);
                }
                randomNumRE = randomNum;

                if (randomNum == 0) {
                    _text = "Sonrie princesa";
                } else if (randomNum == 1) {
                    _text = "no estes triste";
                } else if (randomNum == 2) {
                    _text = ":)";
                } else {
                    _text = "acho alegrate";
                }

            } else if (update.getMessage().getText().contains("ntentar ")) {
                typeMsg = 1;
                _chatid = update.getMessage().getChatId();
                _reply = null;

                String message = update.getMessage().getText();
                String[] parts = message.split("ntentar ");
                String part1 = parts[0]; // intentar
                String part2 = parts[1]; // intento

                int randomNum = ThreadLocalRandom.current().nextInt(0, 5);
                while (randomNum == randomNumRE) {
                    randomNum = ThreadLocalRandom.current().nextInt(0, 5);
                }
                randomNumRE = randomNum;

                if (randomNum == 2) {
                    _text = EmojiParser.parseToUnicode(":game_die: " + update.getMessage().getFrom().getFirstName() + " intento " + part2 + " y lo consigue");
                } else {
                    _text = EmojiParser.parseToUnicode(":game_die: " + update.getMessage().getFrom().getFirstName() + " intento " + part2 + " pero falló");
                }

            } else if (update.getMessage().getText().contains("charcodeagua")) {
                typeMsg = 1;
                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();
                _text = "Ejecutando Orden <charco de agua>";
                ordercda = 1;
            } else if (update.getMessage().getText().contains("ugerencia: ")) {
                typeMsg = 1;
                String message = update.getMessage().getText();
                String[] parts = message.split("ugerencia: ");
                String part1 = parts[0];
                String part2 = parts[1];

                BufferedWriter bw = null;
                FileWriter fw = null;

                try {
                    fw = new FileWriter("sugerencias.txt", true);
                    String text = "@" + update.getMessage().getFrom().getUserName() + " - " + update.getMessage().getFrom().getFirstName() + ": " + part2 + "\n";
                    bw = new BufferedWriter(fw);
                    bw.write(text);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (bw != null)
                            bw.close();
                        if (fw != null)
                            fw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();
                _text = EmojiParser.parseToUnicode("Sugerencia guardada, gracias " + update.getMessage().getFrom().getFirstName() + "! :smile:");
            }
        } else {
            System.out.println("Sticker ID: " + update.getMessage().getSticker().getFileId());
        }

        //----- GOD ORDERS ---------
        if ("/setgod".equals(update.getMessage().getText())) {
            if (ordergod == 0) {
                typeMsg = 1;
                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();
                ordergod = 1;
                godID = update.getMessage().getFrom().getId();
                godChatID = update.getMessage().getChatId();
                godName = update.getMessage().getFrom().getUserName();
                _text = "Estableciendo nuevo dios 1: @" + update.getMessage().getFrom().getUserName() + "\nDios ID: " + godID;
            } else if (ordergod2 == 0) {
                typeMsg = 1;
                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();
                ordergod2 = 1;
                godID2 = update.getMessage().getFrom().getId();
                godChatID2 = update.getMessage().getChatId();
                godName2 = update.getMessage().getFrom().getUserName();
                _text = "Estableciendo nuevo dios 2: @" + update.getMessage().getFrom().getUserName() + "\nDios ID: " + godID2;
            }
        }

        if (update.getMessage().getFrom().getId().equals(godID) || update.getMessage().getFrom().getId().equals(godID2)) {
            if (update.getMessage().getText().contains("/run ")) {
                String message = update.getMessage().getText();
                String[] parts = message.split("/run ");
                String part1 = parts[0];
                String part2 = parts[1];
                typeMsg = 1;
                _text = Command(part2);
                _reply = update.getMessage().getMessageId();
                _chatid = update.getMessage().getChatId();
            } else if (update.getMessage().getText().contains("/chat ")) {
                String message = update.getMessage().getText();
                String[] parts = message.split("/chat ");
                String part1 = parts[0];
                String part2 = parts[1];
                typeMsg = 1;
                _text = Command("cat " + part2 + ".txt");
                _reply = update.getMessage().getMessageId();
                _chatid = update.getMessage().getChatId();
            } else if ("/sugerencias".equals(update.getMessage().getText())) {
                typeMsg = 1;
                _text = Command("cat sugerencias.txt");
                _reply = update.getMessage().getMessageId();
                _chatid = update.getMessage().getChatId();
            } else if ("/unsetgod".equals(update.getMessage().getText())) {
                if (ordergod == 1 || ordergod2 == 1) {
                    if (ordergod == 1) {
                        typeMsg = 1;
                        _chatid = update.getMessage().getChatId();
                        _reply = update.getMessage().getMessageId();
                        _text = "@" + update.getMessage().getFrom().getUserName() + " ya no es el dios 1";
                        ordergod = 0;
                        godID = null;
                        godName = null;
                        godChatID = null;
                    } else if (ordergod2 == 1) {
                        typeMsg = 1;
                        _chatid = update.getMessage().getChatId();
                        _reply = update.getMessage().getMessageId();
                        _text = "@" + update.getMessage().getFrom().getUserName() + " ya no es el dios 2";
                        ordergod2 = 0;
                        godID2 = null;
                        godName2 = null;
                        godChatID = null;
                    }
                }
            } else if (update.getMessage().getText().contains("/info")) {
                typeMsg = 1;
                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();

                String message = update.getMessage().getText();
                String[] parts = message.split("/info ");
                String part1 = parts[0];
                String part2 = parts[1];
                switch (part2) {
                    case "god 1":
                        if (godName != null) {
                            _text = "Dios 1 establecido: @" + godName + "\nDios ID: " + godID;
                        } else {
                            _text = "No hay dios 1 establecido";
                        }
                        break;
                    case "god 2":
                        if (godName2 != null) {
                            _text = "Dios 2 establecido: @" + godName2 + "\nDios ID: " + godID2;
                        } else {
                            _text = "No hay dios 2 establecido";
                        }
                        break;
                    case "chat":
                        if (update.getMessage().getChat().isUserChat()) {
                            _text = "Chat actual: @" + update.getMessage().getChat().getUserName() + "\nChat ID: " + update.getMessage().getChat().getId();
                        } else if (update.getMessage().getChat().isGroupChat()) {
                            _text = "Chat actual: " + update.getMessage().getChat().getTitle() + "\nChat ID: " + update.getMessage().getChat().getId();
                        } else {
                            _text = "*Chat actual: " + update.getMessage().getChat().getTitle() + "\nChat ID: " + update.getMessage().getChat().getId();
                        }
                        break;
                    case "setuser":
                        if (userSelectedID != null) {
                            _text = "Usuario establecido: \nID: " + userSelectedID;
                        } else {
                            _text = "No hay usuario establecido";
                        }
                        break;
                    case "maingroup":
                        if (mainGroupID != null) {
                            _text = "Grupo principal: " + mainGroupName + "\nChat ID: " + mainGroupID;
                        } else {
                            _text = "No hay grupo principal establecido";
                        }
                        break;
                    default:
                        _text = "No puedo mostrar informacion sobre <" + part2 + ">";
                        System.out.println("Error en /info switch");
                        break;
                }

            } else if (update.getMessage().getText().contains("/get")) {

                _chatid = update.getMessage().getChatId();
                String message = update.getMessage().getText();
                String[] parts = message.split("/get ");
                String part2 = parts[1];

                try {
                    File file = new File(part2);
                    typeMsg = 4;
                    _document = file;
                    _reply = update.getMessage().getMessageId();
                    _text = null;
                } catch (Exception e) {
                    typeMsg = 1;
                    _chatid = update.getMessage().getChatId();
                    _reply = update.getMessage().getMessageId();
                    _text = "No puedo enviarte <" + part2 + ">";
                    System.out.println("Error en /get");
                }

            } else if ("/help".equals(update.getMessage().getText())) {
                typeMsg = 1;
                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();
                _text = "COMANDOS:\n/help\n muestra ayuda\n/run <comando>\nejecuta comandos\n/list <algo>\n te lista informacion sobre algo\n escribe \"/help list\" para mas ayuda\n/chat <usuario>\n lista la conversación\n/info <algo>\n escribe \"/help info\" para mas ayuda\n/get <archivo>\n escribe \"/help get\" para mas ayuda\n/setmaingroup\n establece grupo principal\n/unsetmaingroup\n quita el grupo principal establecido\n/send <texto>\n envia un mensaje al grupo principal\n/sethate <alguien>\n el bot odiara esa persona\n/unsethate\n ya no odiara a la persona establecida\n/setuser <ID de usuario>\n establece un usuario, al cual le podras enviar mensajes\n/unsetuser\n quita el usuario establecido\n/xsend <texto>\n envia un mensaje a el usuario establecido\nZONA PELIGROSA\n/unsetgod\n  ya no seras dios\n/shutdown\n apaga el bot";
            } else if ("/help info".equals(update.getMessage().getText())) {
                typeMsg = 1;
                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();
                _text = "/info <algo>\n muestra informacion sobre algo, por ejemplo:\n/info god 1\n/info god 2\n/info chat";
            } else if ("/help get".equals(update.getMessage().getText())) {
                typeMsg = 1;
                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();
                _text = "/get <archivo>\n te envia un documento, ejemplos:\n/get sugerencias.txt\n si es un grupo y contiene espacios, tienes que ponerlo con guiones, ej:\n /get Grupo-test.txt";
            } else if ("/help list".equals(update.getMessage().getText())) {
                typeMsg = 1;
                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();
                _text = "/list <algo>\n te lista algo, ejemplos:\n/list chats\n te lista los usuarios que han hablado al bot\n/list groups\nte lista los grupos en los que ha estado el bot\n/list maingroup\n lista los usuarios de el grupo principal";
            } else if ("/setmaingroup".equals(update.getMessage().getText())) {
                typeMsg = 1;
                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();
                if (mainGroupID != null) {
                    if (update.getMessage().getChat().isGroupChat() || update.getMessage().getChat().isSuperGroupChat() || update.getMessage().getChat().isChannelChat()) {
                        mainGroupName = update.getMessage().getChat().getTitle();
                        mainGroupID = update.getMessage().getChatId();
                        _text = "Grupo principal cambiado\nGrupo: " + mainGroupName + "\nChat ID: " + mainGroupID;
                    } else {
                        _text = "Esto no es un grupo";
                    }
                } else {
                    if (update.getMessage().getChat().isGroupChat()) {
                        mainGroupName = update.getMessage().getChat().getTitle();
                        mainGroupID = update.getMessage().getChatId();
                        _text = "Grupo principal establecido\nGrupo: " + mainGroupName + "\nChat ID: " + mainGroupID;
                    } else {
                        _text = "Esto no es un grupo";
                    }
                }
            } else if ("/unsetmaingroup".equals(update.getMessage().getText())) {
                typeMsg = 1;
                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();
                if (mainGroupID != null) {
                    _text = "Grupo principal desestablecido";
                    mainGroupName = null;
                    mainGroupID = null;
                } else {
                    _text = "No hay grupo principal establecido";
                }
                //////////////////////////////////////////////////
            } else if (update.getMessage().getText().contains("/setuser ")) {
                typeMsg = 1;
                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();

                String message = update.getMessage().getText();
                String[] parts = message.split("/setuser ");
                String part2 = parts[1];
                try {
                    _text = "Usuario principal establecido\n ID: " + userSelectedID;
                    userSelectedID = Long.parseLong(part2);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    _text = "ID invalida, introduce una ID valida";
                }
            } else if ("/unsetuser".equals(update.getMessage().getText())) {
                typeMsg = 1;
                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();
                if (userSelectedID != null) {
                    _text = "Grupo principal desestablecido";
                    userSelectedID = null;
                    userSelectedName = null;
                } else {
                    _text = "No hay grupo principal establecido";
                }
                //////////////////////////////////////////////////
            } else if (update.getMessage().getText().contains("/sethate ")) {
                typeMsg = 1;
                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();

                String message = update.getMessage().getText();
                String[] parts = message.split("/sethate ");
                String part2 = parts[1];
                hate = part2;
                _text = "Odio establecido a \"" + hate + "\"";
            } else if ("/unsethate".equals(update.getMessage().getText())) {
                typeMsg = 1;
                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();
                if (hate == null) {
                    _text = "No hay odio establecido";
                } else {
                    _text = "Ya no existe odio a \"" + hate + "\"";
                    hate = null;
                }
            } else if (update.getMessage().getText().contains("/list ")) {
                typeMsg = 1;
                _chatid = update.getMessage().getChatId();
                _reply = update.getMessage().getMessageId();

                String message = update.getMessage().getText();
                String[] parts = message.split("/list ");
                String part2 = parts[1];
                switch (part2) {
                    case "chats":
                        //_text = Command("ls");
                        _text = logUsers();
                        break;
                    case "groups":
                        _text = logGroups();
                        break;
                    case "maingroup":
                        _text = logGroupUsers(mainGroupName);
                        break;
                    default:
                        _text = "No puedo listarte <" + part2 + ">";
                        break;
                }
            } else if (update.getMessage().getText().contains("/send")) {
                typeMsg = 1;
                if (mainGroupID != null) {
                    String message = update.getMessage().getText();
                    String[] parts = message.split("/send ");
                    String part2 = parts[1];
                    _chatid = mainGroupID;
                    _reply = null;
                    _text = part2;
                } else {
                    _chatid = update.getMessage().getChatId();
                    _reply = update.getMessage().getMessageId();
                    _text = "No hay grupo principal establecido";
                }
            } else if (update.getMessage().getText().contains("/xsend")) {
                typeMsg = 1;
                if (userSelectedID != null) {
                    String message = update.getMessage().getText();
                    String[] parts = message.split("/xsend ");
                    String part2 = parts[1];
                    _chatid = userSelectedID;
                    _reply = null;
                    _text = part2;
                } else {
                    _chatid = update.getMessage().getChatId();
                    _reply = update.getMessage().getMessageId();
                    _text = "No hay grupo usuario establecido";
                }
            } else if ("/shutdown".equals(update.getMessage().getText())) {

                System.exit(0);
            }
        }
        //----- ----------- ---------
        if (typeMsg == 1) {
            try {
                execute(s_msg(_text, _chatid, _reply));
                System.out.println("Mensaje envido...");
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (typeMsg == 2) {
            try {
                sendPhoto(s_pht(_text, _photo, _chatid, _reply));
                System.out.println("Foto enviada...");
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (typeMsg == 3) {
            try {
                sendVideo(s_vid(_text, _photo, _chatid, _reply));
                System.out.println("Video/Gif enviado...");
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (typeMsg == 4) {
            try {
                sendDocument(s_doc(_text, _document, _chatid, _reply));
                System.out.println("Documento enviado...");
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (typeMsg == 5) {
            try {
                sendSticker(s_sti(_chatid, _sticker));
                System.out.println("Sticker enviado...");
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        if (ordercda == 1) {
            String grpName = update.getMessage().getChat().getTitle();
            String usName = update.getMessage().getFrom().getUserName();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            _text = "Llamando a mi primer dios ...";
            _reply = null;
            _chatid = update.getMessage().getChatId();

            //sending order charcodeagua
            try {
                execute(s_msg(_text, _chatid, _reply));
                System.out.println("Orden charco de agua ejecutada");
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            //-----------------------------------------------------------------------------
            if (update.getMessage().getChat().isUserChat()) {
                _text = "Te llama @" + usName + "\nChat ID: " + _chatid;
            } else if (update.getMessage().getChat().isGroupChat()) {
                _text = "Te llaman del grupo: " + grpName + "\nChat ID: " + _chatid;
            } else {
                _text = "*Te llaman del grupo: " + grpName + "\nChat ID: " + _chatid;
            }

            _chatid = godChatID;
            _reply = null;
            try {
                execute(s_msg(_text, _chatid, _reply));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }

    public String getBotUsername() { return "MariaChanBot"; }

    public String getBotToken() { return "BOT_TOKEN"; }
}
