package Theresa.patch;

import Theresa.save.TheresaSave;
import Theresa.save.TheresaSaveAndContinue;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

public class SaveAndContinuePatch {
    public static TheresaSave theresaSave = new TheresaSave();

    @SpirePatch(clz = SaveAndContinue.class, method = "save")
    public static class SavePatch {
        @SpirePostfixPatch
        public static void Postfix(SaveFile save) {
            theresaSave.onSave();
            TheresaSaveAndContinue.saveTheresa(theresaSave);
        }
    }

    @SpirePatch(clz = SaveAndContinue.class, method = "loadSaveFile",paramtypez = {AbstractPlayer.PlayerClass.class})
    public static class LoadPatch {
        @SpirePostfixPatch
        public static SaveFile Postfix(SaveFile _ret, AbstractPlayer.PlayerClass c) {
            TheresaSave e = TheresaSaveAndContinue.loadTheresa(c);
            if(e!=null){
                theresaSave = e;
                theresaSave.onLoad();
            }
            return _ret;
        }
    }

    @SpirePatch(clz = SaveAndContinue.class,method = "deleteSave")
    public static class DeletePatch{
        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer p){
            theresaSave.onDelete();
            TheresaSaveAndContinue.deleteTheresa(p.chosenClass);
        }
    }
}
