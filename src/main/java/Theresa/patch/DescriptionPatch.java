package Theresa.patch;

import Theresa.helper.StringHelper;
import Theresa.silk.AbstractSilk;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class DescriptionPatch {
    private static final UIStrings uiStrings = StringHelper.TAGS;

    @SpirePatch(clz = AbstractCard.class,method = "initializeDescription")
    public static class RenderCardTipPatch{
        @SpirePostfixPatch
        public static void Postfix(AbstractCard _inst){
            ArrayList<String> keywordsToAdd = new ArrayList<>();
            for(String keyword:_inst.keywords){
                if(keyword.equals(uiStrings.TEXT[0])){
                    keywordsToAdd.add(uiStrings.TEXT[1]);
                }
                if(keyword.equals(uiStrings.TEXT[2])){
                    keywordsToAdd.add(uiStrings.TEXT[3]);
                }
                if(keyword.equals(uiStrings.TEXT[5])||keyword.equals(uiStrings.TEXT[6])){
                    keywordsToAdd.add(uiStrings.TEXT[7]);
                }
            }
            for(String keyword:keywordsToAdd){
                if(!_inst.keywords.contains(keyword))
                    _inst.keywords.add(keyword);
            }
        }
    }

    @SpirePatch(clz = TipHelper.class, method = "renderKeywords")
    public static class RenderKeywordsPatch{
        @SpirePrefixPatch
        public static void Prefix(float x, float y, SpriteBatch sb, ArrayList<String> keywords){
            AbstractCard card = ReflectionHacks.getPrivateStatic(TipHelper.class, "card");
            if(card!=null){
                //丝线展示
                AbstractSilk silkP = SilkPatch.SilkCardField.silkForPreview.get(card);
                if(silkP!=null && silkP.name!=null){
                    if(!keywords.contains(silkP.name))
                        keywords.add(silkP.name);
                    //原值覆盖
                    GameDictionary.keywords.put(silkP.name,silkP.description);
                }

                AbstractSilk silk = SilkPatch.SilkCardField.silk.get(card);
                if(silk!=null && silk.name!=null){
                    if(!keywords.contains(silk.name))
                        keywords.add(silk.name);
                    //原值覆盖
                    GameDictionary.keywords.put(silk.name,silk.description);
                }
            }
        }
    }
}
