package Theresa.patch;

import Theresa.helper.CivilightHelper;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class CivilightPatch {
//    @SpirePatch(clz = AbstractDungeon.class,method = SpirePatch.CONSTRUCTOR,paramtypez = {String.class,String.class, AbstractPlayer.class, ArrayList.class})
//    public static class CivilightConstructorPatch {
//        @SpirePostfixPatch
//        public static void Postfix(AbstractDungeon _inst, String name, String levelId, AbstractPlayer p, ArrayList<String> newSpecialOneTimeEventList){
//            if(AbstractDungeon.player!=null && AbstractDungeon.player.masterDeck!=null){
//                CardGroup master = AbstractDungeon.player.masterDeck;
//                if(master.group!=null){
//                    CivilightHelper.cardSaveList.clear();
//                    CivilightHelper.cardSaveList.addAll(master.group);
//                }
//            }
//        }
//    }

    @SpirePatch(clz = CardGroup.class,method = "removeCard",paramtypez = {AbstractCard.class})
    public static class RemoveCardPatch {
        @SpirePostfixPatch
        public static void Postfix(CardGroup _instance, AbstractCard c) {
            if(_instance.type == CardGroup.CardGroupType.MASTER_DECK){
                CivilightHelper.cardSaveList.add(c);
            }
        }
    }
}
