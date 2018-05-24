import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.send.SendVideo;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.concurrent.ThreadLocalRandom;

public class bot extends TelegramLongPollingBot {

    private long actual_chatId;

    private int MSGorPHT; //1 - MSG | 2 - PHOTO | 3 - VIDEO
    private String _photo;
    private Long _chatid;
    private String _text;
    private Integer _reply;

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

    public void onUpdateReceived(Update update) {

        if(actual_chatId == update.getMessage().getChatId()) {
            System.out.println(" [" + update.getMessage().getFrom().getUserName() + "]: " + update.getMessage().getText());
        } else {
            if(update.getMessage().getChat().isUserChat() == true) {
                System.out.println("#User: " + update.getMessage().getFrom().getUserName());
            } else if(update.getMessage().getChat().isGroupChat() == true) {
                System.out.println("#Group: " + update.getMessage().getChat().getTitle());
            } else if (update.getMessage().getChat().isChannelChat() == true) {
                System.out.println("#Channel: " + update.getMessage().getChat().getTitle());
            } else if(update.getMessage().getChat().isSuperGroupChat() == true) {
                System.out.println("#S-Group: " + update.getMessage().getChat().getTitle());
            } else {
                System.out.println("#WTF: " + update.getMessage().getChat().getTitle());
            }
            System.out.println(" [" + update.getMessage().getFrom().getUserName() + "]: " + update.getMessage().getText());
            actual_chatId = update.getMessage().getChatId();
        }

        //----------------------------------------------------

        //....................................................
        //Clear message
        MSGorPHT = 0;
        _text = null;
        _photo = null;
        //....................................................
        if("omae wa mou shindeiru".equals(update.getMessage().getText())) {
            MSGorPHT = 3;
            _text = null;
            _photo = "https://i.makeagif.com/media/2-21-2015/RDVwim.gif";
            _chatid = update.getMessage().getChatId();
            _reply = update.getMessage().getMessageId();
        } else if ("nani".equals(update.getMessage().getText()) || "NANI".equals(update.getMessage().getText()) || "Nani".equals(update.getMessage().getText())) {
                MSGorPHT = 3;
                _text = null;
            _photo = "https://thumbs.gfycat.com/BlandOrnateGreatwhiteshark-max-1mb.gif";
            _chatid = update.getMessage().getChatId();
            _reply = update.getMessage().getMessageId();
        } else if ("what".equals(update.getMessage().getText()) || "WHAT".equals(update.getMessage().getText()) || "What".equals(update.getMessage().getText())) {
            MSGorPHT = 3;
            _text = null;
            _photo = "https://media.giphy.com/media/5QTLUC40nxc1lobJGp/giphy.gif";
            _chatid = update.getMessage().getChatId();
            _reply = update.getMessage().getMessageId();
        } else if (update.getMessage().getText().contains("bot") == true) {

            //generate random number
            int randomNum = ThreadLocalRandom.current().nextInt(0, 3);

            MSGorPHT = 1;
            _chatid = update.getMessage().getChatId();
            _reply = null;

            //all phrases
            if(update.getMessage().getText().contains("puto") || update.getMessage().getText().contains("tonto") || update.getMessage().getText().contains("inutil")) {
                if(randomNum == 1) {
                    _text = "eh, sin insultar xd";
                } else if (randomNum == 2) {
                    _text = "no digas palabrotas";
                } else  if (randomNum == 3) {
                    _text = ":c";
                } else {
                    _text = "vaya tela...";
                }
            } else {
                if(randomNum == 1) {
                    _text = "claro, es por que soy un bot verdad?";
                } else if (randomNum == 2) {
                    _text = "que pasa aqui?";
                } else  if (randomNum == 3) {
                    _text = "hablando de bots... xD";
                } else {
                    _text = "uwu";
                }
            }

        }

        if(MSGorPHT == 1) {
            try {
                execute(s_msg(_text, _chatid, _reply));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (MSGorPHT == 2) {
            try {
                sendPhoto(s_pht(_text, _photo, _chatid, _reply));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (MSGorPHT == 3) {
            try {
                sendVideo(s_vid(_text, _photo, _chatid, _reply));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }


    }

    public String getBotUsername() {
        return "BOT_USERNAME";
    }

    public String getBotToken() {
        return "BOT_TOKEN";
    }
}
