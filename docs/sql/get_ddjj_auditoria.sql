
--drop procedure dbo.get_ddjj_auditoria
--Parámetros: @id_compania, @id_cuenta, @id_modo_operacion
--Valida que no vengan ambos nulos (id_cuenta e id_modo_operacion)
--Busca primero en modo_operacion_det (tomando la versión más reciente por fv_modo_operacion)
--Si falta dato de ddjj y auditoria en modo_operacion_det (o no encontró fila), completa desde cuenta
--Devuelve ddjj, auditoria, operador e insertado

IF OBJECT_ID('dbo.get_ddjj_auditoria') IS NOT NULL
BEGIN
    DROP PROCEDURE dbo.get_ddjj_auditoria
    IF OBJECT_ID('dbo.get_ddjj_auditoria') IS NOT NULL
        PRINT '<<< FAILED DROPPING PROCEDURE dbo.get_ddjj_auditoria >>>'
    ELSE
        PRINT '<<< DROPPED PROCEDURE dbo.get_ddjj_auditoria >>>'
END
GO
create procedure dbo.get_ddjj_auditoria(
    @id_compania       smallint,
    @id_cuenta         int      = null,
    @id_modo_operacion char(6)  = null
    )
as
begin
	set compatibility_mode off

    declare @ddjj tinyint,
			@auditoria tinyint,
			@operador varchar(16),
			@insertado datetime,
			@encontro_mop tinyint

    select @ddjj = null, @auditoria = null, @operador = null, @insertado = null, @encontro_mop = 0

    /* Validación mínima */
    if @id_compania is null
    begin
        raiserror 20000 "El parámetro id_compania es obligatorio"
        return 1
    end

    if @id_cuenta is null and @id_modo_operacion is null
    begin
        raiserror 20001 "Debe informar el parámetro id_cuenta o id_modo_operacion"
        return 1
    end

    /* 1) Prioridad: modo_operacion_det (última vigencia) */
    if @id_modo_operacion is not null
    begin
        select
            @ddjj         = m.ddjj,
            @auditoria    = m.auditoria,
            @operador     = m.operador,
            @insertado    = m.insertado,
            @encontro_mop = 1
        from dbo.modo_operacion_det m
        where m.id_compania       = @id_compania
          and m.id_modo_operacion = @id_modo_operacion
          and m.fv_modo_operacion = (
                select max(m2.fv_modo_operacion)
                from dbo.modo_operacion_det m2
                where m2.id_compania       = m.id_compania
                  and m2.id_modo_operacion = m.id_modo_operacion
          )
          
        IF @@error <> 0 
		BEGIN 
			RAISERROR 99999 "Error buscando datos del modo operación." 
		END 
    end

	/* 2) Fallback a cuenta si no encontró o vino null en modo_operacion_det */
	if @id_cuenta is not null
	   and ((@ddjj is null and @auditoria is null) or @encontro_mop = 0)
		begin
			select
				@ddjj      = c.ddjj,
				@auditoria = convert(tinyint, c.auditoria),
				@operador  = c.operador,
				@insertado = c.insertado
			from dbo.cuenta c
			where c.id_compania = @id_compania
				and c.id_cuenta = @id_cuenta
			
	end
	
	IF @@error <> 0 
	BEGIN 
		RAISERROR 99999 "Error buscando datos de la cuenta." 
	END  

    select	  
        CASE WHEN @ddjj = 1 THEN 'S' ELSE 'N' END  as ddjj,
        CASE WHEN @auditoria = 1 THEN 'S' ELSE 'N' END as auditoria,
        @operador  as operador,
        @insertado as insertado
end
go
EXEC sp_procxmode 'dbo.get_ddjj_auditoria', 'anymode'
go
IF OBJECT_ID('dbo.get_ddjj_auditoria') IS NOT NULL
    PRINT '<<< CREATED PROCEDURE dbo.get_ddjj_auditoria >>>'
ELSE
    PRINT '<<< FAILED CREATING PROCEDURE dbo.get_ddjj_auditoria >>>'
go
GRANT EXECUTE ON dbo.sp_apio_jerarsalud TO Integral_role