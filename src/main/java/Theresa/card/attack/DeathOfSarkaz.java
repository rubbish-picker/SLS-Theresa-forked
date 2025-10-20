package Theresa.card.attack;

import Theresa.action.TheresaAttackAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.power.buff.HatePower;
import Theresa.power.buff.HopePower;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DeathOfSarkaz extends AbstractTheresaCard {
    public static final String ID = "theresa:DeathOfSarkaz";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public DeathOfSarkaz() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ALL_ENEMY);
        baseDamage = damage = 14;
        baseMagicNumber = magicNumber = 2;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new TheresaAttackAction(true));
        addToBot(new DamageAllEnemiesAction(abstractPlayer,this.multiDamage, damageTypeForTurn,skillEffect));
    }

    @Override
    public void applyPowers() {
        int tmp = baseDamage;
        int hateAmount = 0;
        int hopeAmount = 0;
        AbstractPower hate = AbstractDungeon.player.getPower(HatePower.POWER_ID);
        if(hate != null) {
            hateAmount = hate.amount;
        }
        AbstractPower hope = AbstractDungeon.player.getPower(HopePower.POWER_ID);
        if(hope != null) {
            hopeAmount = hope.amount;
        }
        baseDamage += Math.abs(hateAmount-hopeAmount) * magicNumber;
        super.applyPowers();
        baseDamage = tmp;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int tmp = baseDamage;
        int hateAmount = 0;
        int hopeAmount = 0;
        AbstractPower hate = AbstractDungeon.player.getPower(HatePower.POWER_ID);
        if(hate != null) {
            hateAmount = hate.amount;
        }
        AbstractPower hope = AbstractDungeon.player.getPower(HopePower.POWER_ID);
        if(hope != null) {
            hopeAmount = hope.amount;
        }
        baseDamage += Math.abs(hateAmount-hopeAmount) * magicNumber;
        super.calculateCardDamage(mo);
        baseDamage = tmp;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeMagicNumber(1);
        }
    }
}
