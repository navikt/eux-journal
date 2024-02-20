create table feilregistrering
(
    feilregistrering_uuid   uuid primary key not null,
    feilregistrering_status text             not null,
    beskrivelse             text             not null,
    dokument_info_id        text             not null,
    journalpost_id          text             not null,
    sed_id                  uuid             not null,
    opprettet_bruker        text             not null,
    opprettet_tidspunkt     timestamp        not null
);
