CREATE OR REPLACE FUNCTION fn_cpf_trigger() RETURNS TRIGGER AS $$
DECLARE
v_cpf_limpo TEXT;
    v_caldv1 INT;
    v_caldv2 INT;
    v_dv1 INT;
    v_dv2 INT;
BEGIN
    -- Limpa a formatação do CPF
    v_cpf_limpo := translate(NEW.cpf, './-', '');

    IF length(v_cpf_limpo) = 11 THEN
        -- Extrai os dígitos verificadores
        v_dv1 := CAST(substring(v_cpf_limpo, 10, 1) AS INTEGER);
        v_dv2 := CAST(substring(v_cpf_limpo, 11, 1) AS INTEGER);
        v_cpf_limpo := substring(v_cpf_limpo, 1, 9);

        -- Cálculo do primeiro dígito verificador
        v_caldv1 := 0;
FOR i IN 1..9 LOOP
            v_caldv1 := v_caldv1 + CAST(substring(v_cpf_limpo, i, 1) AS INTEGER) * (11 - i);
END LOOP;
        v_caldv1 := CASE WHEN v_caldv1 % 11 < 2 THEN 0 ELSE 11 - (v_caldv1 % 11) END;

        -- Cálculo do segundo dígito verificador
        v_caldv2 := 0;
FOR i IN 1..10 LOOP
            v_caldv2 := v_caldv2 + CAST(substring(v_cpf_limpo || v_caldv1::TEXT, i, 1) AS INTEGER) * (12 - i);
END LOOP;
        v_caldv2 := CASE WHEN v_caldv2 % 11 < 2 THEN 0 ELSE 11 - (v_caldv2 % 11) END;

        -- Verifica se os dígitos calculados correspondem aos informados
        IF v_caldv1 = v_dv1 AND v_caldv2 = v_dv2 THEN
            NEW.cpf := v_cpf_limpo; -- CPF válido, atualiza o CPF no NEW
RETURN NEW;
ELSE
            RAISE EXCEPTION 'CPF inválido!' USING ERRCODE = '22023'; -- Define o SQLState para CPF inválido
END IF;
ELSE
        RAISE EXCEPTION 'CPF inválido!' USING ERRCODE = '22023'; -- Define o SQLState para CPF inválido
END IF;
END;
$$ LANGUAGE plpgsql;
