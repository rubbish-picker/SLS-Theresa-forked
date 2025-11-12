package Theresa.modifier;

import Theresa.helper.StringHelper;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;

import java.util.ArrayList;

public class ForgetMod extends AbstractCardModifier {
    public static String ID = "theresa:ForgetMod";

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        ArrayList<AbstractCardModifier> modifiers = CardModifierManager.getModifiers(card,ID);
        if(!modifiers.isEmpty()&&modifiers.get(0)==this) {
            int amount = modifiers.size();
            return rawDescription + " NL " + StringHelper.TAGS.TEXT[8]+ " " + amount+ (Settings.lineBreakViaCharacter ? " " : "") + LocalizedStrings.PERIOD;
        }
        return rawDescription;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return true;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {

    }

    @Override
    public void onRemove(AbstractCard card) {

    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return false;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ForgetMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}

