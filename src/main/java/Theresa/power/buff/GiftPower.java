package Theresa.power.buff;

import Theresa.card.attack.TheBlooder;
import Theresa.power.AbstractTheresaPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class GiftPower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:GiftPower";
    public GiftPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        setAmountDescription();
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if(card.cardID == TheBlooder.ID)
            return damage + this.amount;
        return super.atDamageGive(damage, type, card);
    }
}
