package Theresa.power.buff;

import Theresa.action.DustAction;
import Theresa.power.AbstractTheresaPower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

public class ThousandsWishPower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:ThousandsWishPower";

    public int extraAmount;
    private final Color greenColor = new Color(0.0F, 1.0F, 0.0F, 1.0F);
    private static final float DISTANCE = 17F * Settings.scale;

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);
        if (!this.isTurnBased) {
            this.greenColor.a = c.a;
            c = this.greenColor;
        }
        FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.extraAmount), x, y+DISTANCE, this.fontScale, c);
    }

    public ThousandsWishPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        extraAmount = 0;
        updateDescription();
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        extraAmount++;
        if(extraAmount >= 10){
            extraAmount -= 10;
            this.flash();
            addToBot(new DustAction(this.amount));
        }
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1] + extraAmount;
    }
}
