-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 31-01-2025 a las 18:28:07
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bdtarea3ad`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carnets`
--

CREATE TABLE `carnets` (
  `id` bigint(20) NOT NULL,
  `distancia` float(5,1) DEFAULT NULL,
  `fecha_exp` date NOT NULL DEFAULT current_timestamp(),
  `n_vip` int(11) NOT NULL DEFAULT 0,
  `pinicial_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `carnets`
--

INSERT INTO `carnets` (`id`, `distancia`, `fecha_exp`, `n_vip`, `pinicial_id`) VALUES
(1, 60.5, '2025-01-31', 3, 4),
(2, 0.0, '2025-01-31', 0, 2),
(3, 0.0, '2025-01-31', 0, 3),
(4, 0.0, '2025-01-31', 0, 4),
(5, 11.0, '2025-01-31', 1, 5),
(6, 5.5, '2025-01-31', 1, 4),
(7, 11.0, '2025-01-31', 0, 2),
(8, 0.0, '2025-01-31', 0, 2),
(9, 0.0, '2025-01-31', 0, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estancias`
--

CREATE TABLE `estancias` (
  `id` bigint(20) NOT NULL,
  `fecha` date NOT NULL,
  `parada_id` bigint(20) NOT NULL,
  `peregrino_id` bigint(20) NOT NULL,
  `vip` tinyint(1) NOT NULL,
  `fechastring` varchar(255) DEFAULT NULL,
  `vip_status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `estancias`
--

INSERT INTO `estancias` (`id`, `fecha`, `parada_id`, `peregrino_id`, `vip`, `fechastring`, `vip_status`) VALUES
(1, '2025-01-31', 1, 1, 0, NULL, NULL),
(2, '2025-01-31', 1, 1, 1, NULL, NULL),
(3, '2025-01-31', 5, 1, 1, NULL, NULL),
(4, '2025-01-31', 5, 1, 0, NULL, NULL),
(5, '2025-01-31', 4, 1, 1, NULL, NULL),
(6, '2025-01-31', 1, 5, 1, NULL, NULL),
(7, '2025-01-31', 3, 7, 0, NULL, NULL),
(8, '2025-01-31', 3, 6, 1, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `paradas`
--

CREATE TABLE `paradas` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `region` char(1) NOT NULL,
  `responsable` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `paradas`
--

INSERT INTO `paradas` (`id`, `nombre`, `region`, `responsable`) VALUES
(1, 'Gijon', 'G', 'gijon'),
(2, 'Aviles', 'A', 'aviles'),
(3, 'Ribadeo', 'R', 'ribadeo'),
(4, 'Ferrol', 'F', 'ferrol'),
(5, 'Oviedo', 'O', 'oviedo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `peregrinos`
--

CREATE TABLE `peregrinos` (
  `id` bigint(20) NOT NULL,
  `id_user` int(11) DEFAULT NULL,
  `nacionalidad` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `peregrinos`
--

INSERT INTO `peregrinos` (`id`, `id_user`, `nacionalidad`, `nombre`) VALUES
(1, 6, 'Emiratos Árabes Unidos', 'natxo espacio test'),
(2, 7, 'Austria', 'luis'),
(3, 8, 'Australia', 'marta'),
(4, 9, 'Brasil', 'laura'),
(5, 10, 'Canadá', 'miriam'),
(6, 11, 'Chile', 'antonio'),
(7, 12, 'Principado de Liechtenstein', 'role'),
(8, 13, 'Austria', 'test final'),
(9, 14, 'Suiza', 'test final final');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ruta`
--

CREATE TABLE `ruta` (
  `id` bigint(20) NOT NULL,
  `distancia` float(5,1) DEFAULT NULL,
  `orden` int(11) NOT NULL,
  `parada_id` bigint(20) NOT NULL,
  `peregrino_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ruta`
--

INSERT INTO `ruta` (`id`, `distancia`, `orden`, `parada_id`, `peregrino_id`) VALUES
(1, 0.0, 1, 4, 1),
(2, 5.5, 2, 1, 1),
(3, 5.5, 3, 1, 1),
(4, 5.5, 4, 1, 1),
(5, 5.5, 5, 1, 1),
(6, 5.5, 6, 5, 1),
(7, 5.5, 7, 5, 1),
(8, 5.5, 8, 5, 1),
(9, 5.5, 9, 4, 1),
(10, 0.0, 1, 2, 2),
(11, 0.0, 1, 3, 3),
(12, 0.0, 1, 4, 4),
(13, 0.0, 1, 5, 5),
(14, 0.0, 1, 4, 6),
(15, 0.0, 1, 2, 7),
(16, 5.5, 2, 1, 5),
(17, 5.5, 3, 1, 5),
(18, 5.5, 2, 3, 7),
(19, 5.5, 3, 3, 7),
(20, 5.5, 2, 3, 6),
(21, 5.5, 10, 3, 1),
(22, 0.0, 1, 2, 8),
(23, 0.0, 1, 3, 9);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `dob` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_user` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `perfil` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_user`, `nombre`, `password`, `perfil`) VALUES
(1, 'gijon', 'gijon', 'parada'),
(2, 'aviles', 'aviles', 'parada'),
(3, 'ribadeo', 'ribadeo', 'parada'),
(4, 'ferrol', 'ferrol', 'parada'),
(5, 'oviedo', 'oviedo', 'parada'),
(6, 'natxoespaciotest', 'natxo', 'peregrino'),
(7, 'luis', 'luis', 'peregrino'),
(8, 'marta', 'marta', 'peregrino'),
(9, 'laura', 'laura', 'peregrino'),
(10, 'miriam', 'miriam', 'peregrino'),
(11, 'antonio', 'antonio', 'peregrino'),
(12, 'role', 'role', 'peregrino'),
(13, 'testfinal', 'final', 'peregrino'),
(14, 'testfinalfinal', 'final', 'peregrino');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `carnets`
--
ALTER TABLE `carnets`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8l3vu4n95ut611pmhq8h3k7px` (`pinicial_id`);

--
-- Indices de la tabla `estancias`
--
ALTER TABLE `estancias`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK50ye2cv83etcb1d8e0lbwksj2` (`parada_id`),
  ADD KEY `FK9gu8y55cpgki1egb1f7ohrt78` (`peregrino_id`);

--
-- Indices de la tabla `paradas`
--
ALTER TABLE `paradas`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `peregrinos`
--
ALTER TABLE `peregrinos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKfo8r2guqgi729p52panvp0uht` (`id_user`);

--
-- Indices de la tabla `ruta`
--
ALTER TABLE `ruta`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK9ux49q4h502uujw3x7lvj2ra` (`parada_id`),
  ADD KEY `FKlr65a2aq8ev2mrfsxl3jjidqd` (`peregrino_id`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `UKio49vjba68pmbgpy9vtw8vm81` (`nombre`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `carnets`
--
ALTER TABLE `carnets`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `estancias`
--
ALTER TABLE `estancias`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `paradas`
--
ALTER TABLE `paradas`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `peregrinos`
--
ALTER TABLE `peregrinos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `ruta`
--
ALTER TABLE `ruta`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `carnets`
--
ALTER TABLE `carnets`
  ADD CONSTRAINT `FK8l3vu4n95ut611pmhq8h3k7px` FOREIGN KEY (`pinicial_id`) REFERENCES `paradas` (`id`);

--
-- Filtros para la tabla `estancias`
--
ALTER TABLE `estancias`
  ADD CONSTRAINT `FK50ye2cv83etcb1d8e0lbwksj2` FOREIGN KEY (`parada_id`) REFERENCES `paradas` (`id`),
  ADD CONSTRAINT `FK9gu8y55cpgki1egb1f7ohrt78` FOREIGN KEY (`peregrino_id`) REFERENCES `peregrinos` (`id`);

--
-- Filtros para la tabla `peregrinos`
--
ALTER TABLE `peregrinos`
  ADD CONSTRAINT `FKfo8r2guqgi729p52panvp0uht` FOREIGN KEY (`id_user`) REFERENCES `usuarios` (`id_user`);

--
-- Filtros para la tabla `ruta`
--
ALTER TABLE `ruta`
  ADD CONSTRAINT `FK9ux49q4h502uujw3x7lvj2ra` FOREIGN KEY (`parada_id`) REFERENCES `paradas` (`id`),
  ADD CONSTRAINT `FKlr65a2aq8ev2mrfsxl3jjidqd` FOREIGN KEY (`peregrino_id`) REFERENCES `peregrinos` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
