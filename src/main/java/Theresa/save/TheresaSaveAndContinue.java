package Theresa.save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.exceptions.SaveFileLoadError;
import com.megacrit.cardcrawl.helpers.AsyncSaver;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import com.megacrit.cardcrawl.saveAndContinue.SaveFileObfuscator;

import java.io.File;

public class TheresaSaveAndContinue {
    public static void saveTheresa(TheresaSave TheresaSave){
        Gson gson = new Gson();
        String data = gson.toJson(TheresaSave);
        String filepath = getTheresaSavePath(AbstractDungeon.player.chosenClass);
        if(Settings.isBeta){
            AsyncSaver.save(filepath + "BETA",data);
        }
        AsyncSaver.save(filepath, SaveFileObfuscator.encode(data,"key"));
    }

    public static TheresaSave loadTheresa(AbstractPlayer.PlayerClass c){
        String fileName = getTheresaSavePath(c);

        try {
            return loadTheresa(fileName);
        } catch (SaveFileLoadError var3) {
            return null;
        }
    }

    public static void deleteTheresa(AbstractPlayer.PlayerClass c){
        String savePath = getTheresaSavePath(c);
        Gdx.files.local(savePath).delete();
        Gdx.files.local(savePath + ".backUp").delete();
    }



    private static TheresaSave loadTheresa(String filePath) throws SaveFileLoadError{
        TheresaSave saveFile = null;
        Gson gson = new Gson();
        String savestr = null;
        Exception err = null;

        try {
            savestr = loadSaveString(filePath);
            saveFile = gson.fromJson(savestr,TheresaSave.class);
        } catch (Exception var6) {
            if (Gdx.files.local(filePath).exists()) {
                SaveHelper.preserveCorruptFile(filePath);
            }

            err = var6;
            if (!filePath.endsWith(".backUp")) {
                return loadTheresa(filePath + ".backUp");
            }
        }

        if (saveFile == null) {
            throw new SaveFileLoadError("Didn't Find Right Now: " + filePath, err);
        } else {
            return saveFile;
        }
    }

    private static String loadSaveString(String filePath) {
        FileHandle file = Gdx.files.local(filePath);
        String data = file.readString();
        return SaveFileObfuscator.isObfuscated(data) ? SaveFileObfuscator.decode(data, "key") : data;
    }

    private static String getTheresaSavePath(AbstractPlayer.PlayerClass c){
        StringBuilder sb = new StringBuilder();
        sb.append("saves" + File.separator + "theresaMod" + File.separator);
        if (CardCrawlGame.saveSlot != 0) {
            sb.append(CardCrawlGame.saveSlot).append("_");
        }

        //WAVE 把这里改成你自己mod就行
        sb.append(c.name()).append(".autosave");
        return sb.toString();
    }
}
