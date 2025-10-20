package Theresa.card.attack;

import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Oscillation extends AbstractTheresaCard {
    public static final String ID = "theresa:Oscillation";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Oscillation() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.COMMON,CardTarget.ENEMY);
        baseDamage = damage = 8;
        baseMagicNumber = magicNumber = 8;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,damage,damageTypeForTurn),attackEffect));
    }

    @Override
    public void triggerWhenBecomeDust() {
        this.flash();
        addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(magicNumber,true), DamageInfo.DamageType.THORNS,skillEffect));
    }

    @Override
    public void triggerWhenNoLongerDust() {
        this.flash();
        addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(magicNumber,true), DamageInfo.DamageType.THORNS,skillEffect));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(3);
        }
    }
}
