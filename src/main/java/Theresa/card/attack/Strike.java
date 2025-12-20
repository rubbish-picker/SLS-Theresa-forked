package Theresa.card.attack;

import Theresa.action.StartShaderAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.effect.FinaleEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Strike extends AbstractTheresaCard {
    public static final String ID = "theresa:Strike";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Strike() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.BASIC,CardTarget.ENEMY);
        baseDamage = damage = 6;
        tags.add(CardTags.STRIKE);
        tags.add(CardTags.STARTER_STRIKE);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        //addToBot(new TheresaAttackAction());
        //addToBot(new VFXAction(abstractPlayer,new FinaleEffect(false),0.2F,true));
        //addToBot(new VFXAction(abstractPlayer,new FinaleEffect(true),0.2F,true));
        int randomNumber = AbstractDungeon.cardRandomRng.random(0, this.damage*2);
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,randomNumber,damageTypeForTurn),attackEffect));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }
}
